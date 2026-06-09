package com.firstclub.membership.services;

import com.firstclub.membership.DTO.MembershipResponseDTO;
import com.firstclub.membership.DTO.SubscribeRequestDTO;
import com.firstclub.membership.entity.*;
import com.firstclub.membership.enums.MembershipAction;
import com.firstclub.membership.enums.MembershipStatus;
import com.firstclub.membership.interfaces.MembershipService;
import com.firstclub.membership.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {
    private final MembershipRepository membershipRepository;
    private final MembershipHistoryRepository membershipHistoryRepository;
    private final UserRepository userRepository;
    private final MembershipPlanRepository membershipPlanRepository;
    private final TiersRepository tiersRepository;

    private MembershipResponseDTO mapToResponse(Membership membership){
        return new MembershipResponseDTO(
                membership.getId(),
                membership.getUser().getId(),
                membership.getUser().getName(),
                membership.getPlan().getName(),
                membership.getTier().getName(),
                membership.getStatus(),
                membership.getStartDate(),
                membership.getExpiryDate()
        );
    }

    @Override
    public MembershipResponseDTO subscribe(SubscribeRequestDTO subscribeRequestDTO) {
        Long userId = subscribeRequestDTO.userId();
        Long membershipPlanId = subscribeRequestDTO.planId();
        Long tierId = subscribeRequestDTO.tierId();

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User not found"));
        MembershipPlan membershipPlan = membershipPlanRepository.findById(membershipPlanId).orElseThrow(() -> new IllegalStateException("Membership plan not found"));
        Tier tier = tiersRepository.findById(tierId).orElseThrow(() -> new IllegalStateException("Tier not found"));

        if(membershipRepository.existsByUser_IdAndStatus(userId, MembershipStatus.ACTIVE)){
            throw new RuntimeException("User already has an active membership");
        }

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expiryDate = startDate.plusDays(membershipPlan.getDurationDays());

        Membership newMembership = Membership.builder()
                .user(user)
                .plan(membershipPlan)
                .tier(tier)
                .status(MembershipStatus.ACTIVE)
                .autoRenew(subscribeRequestDTO.autoRenew())
                .startDate(startDate)
                .expiryDate(expiryDate)
                .build();

        newMembership = membershipRepository.save(newMembership);

        MembershipHistory newMembershipHistory = MembershipHistory.builder()
                .membership(newMembership)
                .oldTier(null)
                .newTier(tier)
                .action(MembershipAction.SUBSCRIBED)
                .remarks("Membership created")
                .createdAt(LocalDateTime.now())
                .build();

        membershipHistoryRepository.save(newMembershipHistory);

        return mapToResponse(newMembership);
    }

    @Override
    @Transactional(readOnly = true)
    public MembershipResponseDTO getMembership(Long id){
        Membership membership = membershipRepository.findById(id).orElseThrow(() -> new IllegalStateException("Membership not found"));

        return new MembershipResponseDTO(
                membership.getId(),
                membership.getUser().getId(),
                membership.getUser().getName(),
                membership.getPlan().getName(),
                membership.getTier().getName(),
                membership.getStatus(),
                membership.getStartDate(),
                membership.getExpiryDate()
        );
    }

    @Override
    public MembershipResponseDTO cancelMembership(Long membershipId){
        Membership membership = membershipRepository.findByIdAndStatus(membershipId, MembershipStatus.ACTIVE).orElseThrow(() -> new RuntimeException("Membership not found"));

        membership.setStatus(MembershipStatus.CANCELLED);

        membership = membershipRepository.save(membership);

        MembershipHistory history = MembershipHistory.builder()
                .membership(membership)
                .oldTier(membership.getTier())
                .newTier(null)
                .action(MembershipAction.CANCELLED)
                .remarks("Membership Cancelled")
                .createdAt(LocalDateTime.now())
                .build();

        membershipHistoryRepository.save(history);

        return mapToResponse(membership);
    }
}
