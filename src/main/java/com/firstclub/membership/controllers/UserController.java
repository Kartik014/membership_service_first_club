package com.firstclub.membership.controllers;

import com.firstclub.membership.DTO.Requests.CreateUserRequestDTO;
import com.firstclub.membership.DTO.Requests.UpdateUserMembershipStatsRequestDTO;
import com.firstclub.membership.DTO.Responses.BenefitResponseDTO;
import com.firstclub.membership.DTO.Responses.MembershipResponseDTO;
import com.firstclub.membership.DTO.Responses.UserMembershipStatsResponseDTO;
import com.firstclub.membership.DTO.Responses.UserResponseDTO;
import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.interfaces.TiersService;
import com.firstclub.membership.interfaces.UserMembershipStatsService;
import com.firstclub.membership.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final TiersService tiersService;
    private final UserMembershipStatsService userMembershipStatsService;

    @GetMapping
    List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    UserResponseDTO addNewUser(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        return userService.addNewUser(createUserRequestDTO);
    }

    @GetMapping("/{id}")
    UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/{id}/memberships")
    public MembershipResponseDTO getUserActiveMembership(@PathVariable Long id){
        return userService.getUserActiveMembership(id);
    }

    @GetMapping("/{userId}/benefits")
    public List<BenefitResponseDTO> getUserBenefits(@PathVariable Long userId){
        return userService.getUserBenefits(userId);
    }

    @GetMapping("/{userId}/evaluate-tier")
    public Tier evaluateUserTier(@PathVariable Long userId){
        return tiersService.evaluateTier(userId);
    }

    @PostMapping("/{userId}/stats")
    public UserMembershipStatsResponseDTO addUserStats(@PathVariable Long userId, @Valid @RequestBody UpdateUserMembershipStatsRequestDTO updateUserMembershipStatsRequestDTO){
        return userMembershipStatsService.addUserStats(userId, updateUserMembershipStatsRequestDTO);
    }
}
