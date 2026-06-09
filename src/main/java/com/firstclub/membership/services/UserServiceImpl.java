package com.firstclub.membership.services;

import com.firstclub.membership.DTO.BenefitResponseDTO;
import com.firstclub.membership.DTO.CreateUserRequestDTO;
import com.firstclub.membership.DTO.MembershipResponseDTO;
import com.firstclub.membership.DTO.UserResponseDTO;
import com.firstclub.membership.entity.Membership;
import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.entity.TierBenefit;
import com.firstclub.membership.entity.User;
import com.firstclub.membership.enums.MembershipStatus;
import com.firstclub.membership.interfaces.UserService;
import com.firstclub.membership.repository.MembershipRepository;
import com.firstclub.membership.repository.TierBenefitRepository;
import com.firstclub.membership.repository.TiersRepository;
import com.firstclub.membership.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final TiersRepository tiersRepository;
    private final TierBenefitRepository tierBenefitRepository;

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
                        user.getEmail(),
                        user.getId()
                ))
                .toList();
    }

    @Override
    public UserResponseDTO addNewUser(CreateUserRequestDTO createUserRequestDTO) {
        if(userRepository.existsByEmail(createUserRequestDTO.email())){
            throw new IllegalStateException("Email already exists");
        }

        User newUser = User.builder()
                .name(createUserRequestDTO.name())
                .cohort(createUserRequestDTO.cohort())
                .email(createUserRequestDTO.email())
                .build();

        newUser = userRepository.save(newUser);

        return mapToResponse(newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUser(Long id){
        if(!userRepository.existsById(id)){
            throw new IllegalStateException("User with id " + id + " does not exist");
        }

        User user = userRepository.findById(id).get();

        return mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public MembershipResponseDTO getUserActiveMembership(Long userId){
        Membership activeMembership = membershipRepository.findByUser_IdAndStatus(userId, MembershipStatus.ACTIVE).orElseThrow(() -> new IllegalStateException("User does not have an active membership"));

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
        Membership membership = membershipRepository.findByUser_IdAndStatus(userId, MembershipStatus.ACTIVE).orElseThrow(() -> new IllegalStateException("User does not have an active membership"));

        Tier tier = tiersRepository.findById(membership.getTier().getId()).orElseThrow(() -> new IllegalStateException("Tier not found"));

        List<TierBenefit> tierBenefits = tierBenefitRepository.findByTier_Id(tier.getId()).orElseThrow(() -> new IllegalStateException("No benefits found for this tier"));

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
