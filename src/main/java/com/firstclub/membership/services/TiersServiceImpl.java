package com.firstclub.membership.services;

import com.firstclub.membership.DTO.Responses.TierRefreshResponseDTO;
import com.firstclub.membership.DTO.Responses.TiersResponseDTO;
import com.firstclub.membership.entity.*;
import com.firstclub.membership.enums.CriteriaType;
import com.firstclub.membership.enums.MembershipAction;
import com.firstclub.membership.enums.MembershipStatus;
import com.firstclub.membership.exceptions.InvalidOperationException;
import com.firstclub.membership.exceptions.ResourceNotFoundException;
import com.firstclub.membership.interfaces.TiersService;
import com.firstclub.membership.repository.*;
import com.firstclub.membership.interfaces.CriteriaEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TiersServiceImpl implements TiersService {
    private final TiersRepository tiersRepository;
    private final UserRepository userRepository;
    private final UserMembershipStatsRepository userMembershipStatsRepository;
    private final TierCriteriaRepository tierCriteriaRepository;
    private final TierEvaluationService tierEvaluationService;
    private final MembershipRepository membershipRepository;
    private final MembershipHistoryRepository membershipHistoryRepository;

    private TiersResponseDTO mapToResponse(Tier tier){
        return new TiersResponseDTO(
                tier.getId(),
                tier.getName(),
                tier.getPriority(),
                tier.getActive()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<TiersResponseDTO> getAllTiers() {
        return tiersRepository
                .findAll()
                .stream()
                .map(tier -> new TiersResponseDTO(
                        tier.getId(),
                        tier.getName(),
                        tier.getPriority(),
                        tier.getActive()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TiersResponseDTO getTier(Long id){
        if(!tiersRepository.existsById(id)){
            throw new RuntimeException("Tier with id " + id + " not found");
        }

        Tier tier = tiersRepository.findById(id).get();

        return mapToResponse(tier);
    }

    @Override
    @Transactional(readOnly = true)
    public Tier evaluateTier(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserMembershipStats stats = userMembershipStatsRepository.findByUser_Id(userId).orElseThrow(() -> new ResourceNotFoundException("User membership stats not found"));

        List<Tier> tiers = tiersRepository.findAllByOrderByPriorityAsc().orElseThrow(() -> new ResourceNotFoundException("Tiers not found"));

        Tier eligibleTier = null;

        for(Tier tier : tiers){
            List<TierCriteria> tierCriteriaList = tierCriteriaRepository.findByTier_Id(tier.getId()).orElseThrow(() -> new ResourceNotFoundException("Tier criteria not found"));
            boolean satisfied = true;

            for(TierCriteria criteria : tierCriteriaList){
                CriteriaEvaluator evaluator = tierEvaluationService.getEvaluator(CriteriaType.valueOf(criteria.getCriteriaType()));
                boolean result = evaluator.evaluate(user, criteria, stats);

                if(!result){
                    satisfied = false;
                    break;
                }
            }

            if(satisfied){
                eligibleTier = tier;
            }
        }

        return eligibleTier;
    }

    @Override
    @Transactional
    public TierRefreshResponseDTO refreshUserTier(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow(() -> new ResourceNotFoundException("Membership not found"));

        if(!membership.getStatus().equals(MembershipStatus.ACTIVE)){
            throw new InvalidOperationException("Membership is not active");
        }

        Tier currentTier = membership.getTier();
        Tier eligibleTier = this.evaluateTier(membership.getUser().getId());

        if(currentTier.getId().equals(eligibleTier.getId())){
            return new TierRefreshResponseDTO(
                    membership.getId(),
                    currentTier.getName(),
                    eligibleTier.getName(),
                    "NO_CHANGE"
            );
        }

        MembershipAction action;

        if(eligibleTier.getPriority() > currentTier.getPriority()){
            action = MembershipAction.UPGRADED;
        } else {
            action = MembershipAction.DOWNGRADED;
        }

        membership.setTier(eligibleTier);

        membershipRepository.save(membership);

        MembershipHistory history = MembershipHistory.builder()
                .oldTier(currentTier)
                .newTier(eligibleTier)
                .membership(membership)
                .action(action)
                .remarks("Automatic Tier refresh")
                .createdAt(LocalDateTime.now())
                .build();

        membershipHistoryRepository.save(history);

        return new TierRefreshResponseDTO(
                membership.getId(),
                currentTier.getName(),
                eligibleTier.getName(),
                action.toString()
        );
    }
}
