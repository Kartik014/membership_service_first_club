package com.firstclub.membership.controllers;

import com.firstclub.membership.DTO.MembershipResponseDTO;
import com.firstclub.membership.DTO.SubscribeRequestDTO;
import com.firstclub.membership.interfaces.MembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memberships")
public class MembershipController {
    private final MembershipService membershipService;

    @PostMapping("/subscribe")
    public MembershipResponseDTO subscribe(@Valid @RequestBody SubscribeRequestDTO subscribeRequestDTO){
        return membershipService.subscribe(subscribeRequestDTO);
    }

    @GetMapping("/{id}")
    public MembershipResponseDTO getMembership(@PathVariable Long id){
        return membershipService.getMembership(id);
    }
}
