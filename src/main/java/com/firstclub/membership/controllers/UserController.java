package com.firstclub.membership.controllers;

import com.firstclub.membership.DTO.BenefitResponseDTO;
import com.firstclub.membership.DTO.CreateUserRequestDTO;
import com.firstclub.membership.DTO.MembershipResponseDTO;
import com.firstclub.membership.DTO.UserResponseDTO;
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
}
