package com.firstclub.membership.services;

import com.firstclub.membership.DTO.Responses.BenefitResponseDTO;
import com.firstclub.membership.DTO.Requests.CreateUserRequestDTO;
import com.firstclub.membership.DTO.Responses.MembershipResponseDTO;
import com.firstclub.membership.DTO.Responses.UserResponseDTO;
import com.firstclub.membership.entity.*;
import com.firstclub.membership.enums.MembershipStatus;
import com.firstclub.membership.enums.UserCohort;
import com.firstclub.membership.exceptions.DuplicateResourceException;
import com.firstclub.membership.exceptions.ResourceNotFoundException;
import com.firstclub.membership.interfaces.UserService;
import com.firstclub.membership.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final TiersRepository tiersRepository;
    private final TierBenefitRepository tierBenefitRepository;
    private final UserMembershipStatsRepository userMembershipStatsRepository;

    private UserResponseDTO mapToResponse(User user){
        return new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getCohort(),
                user.getId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getName(),
                        user.getEmail(),
                        user.getCohort(),
                        user.getId()
                ))
                .toList();
    }

    @Override
    public UserResponseDTO addNewUser(CreateUserRequestDTO createUserRequestDTO) {
        if(userRepository.existsByEmail(createUserRequestDTO.email())){
            throw new DuplicateResourceException("Email already exists");
        }

        User newUser = User.builder()
                .name(createUserRequestDTO.name())
                .cohort(UserCohort.valueOf(createUserRequestDTO.cohort().toString().toUpperCase()))
                .email(createUserRequestDTO.email())
                .build();

        newUser = userRepository.save(newUser);

        UserMembershipStats stats = UserMembershipStats.builder()
                .user(newUser)
                .monthlyOrders(0)
                .monthlySpend(BigDecimal.ZERO)
                .totalOrders(0)
                .lastUpdated(LocalDateTime.now())
                .build();

        userMembershipStatsRepository.save(stats);

        return mapToResponse(newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }

        User user = userRepository.findById(id).get();

        return mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public MembershipResponseDTO getUserActiveMembership(Long userId){
        Membership activeMembership = membershipRepository.findByUser_IdAndStatus(userId, MembershipStatus.ACTIVE).orElseThrow(() -> new ResourceNotFoundException("User does not have an active membership"));

        return new MembershipResponseDTO(
                activeMembership.getId(),
                activeMembership.getUser().getId(),
                activeMembership.getUser().getName(),
                activeMembership.getPlan().getName(),
                activeMembership.getTier().getName(),
                activeMembership.getStatus(),
                activeMembership.getStartDate(),
                activeMembership.getExpiryDate()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BenefitResponseDTO> getUserBenefits(Long userId){
        Membership membership = membershipRepository.findByUser_IdAndStatus(userId, MembershipStatus.ACTIVE).orElseThrow(() -> new ResourceNotFoundException("User does not have an active membership"));

        Tier tier = tiersRepository.findById(membership.getTier().getId()).orElseThrow(() -> new ResourceNotFoundException("Tier not found"));

        List<TierBenefit> tierBenefits = tierBenefitRepository.findByTier_Id(tier.getId()).orElseThrow(() -> new ResourceNotFoundException("No benefits found for this tier"));

        return tierBenefits
                .stream()
                .map(tb -> new BenefitResponseDTO(
                        tb.getBenefit().getId(),
                        tb.getBenefit().getName(),
                        tb.getBenefit().getBenefitType()
                ))
                .toList();
    }
}
