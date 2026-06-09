package com.firstclub.membership.controllers;

import com.firstclub.membership.DTO.MembershipHistoryResponseDTO;
import com.firstclub.membership.DTO.MembershipResponseDTO;
import com.firstclub.membership.DTO.SubscribeRequestDTO;
import com.firstclub.membership.interfaces.MembershipHistoryService;
import com.firstclub.membership.interfaces.MembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memberships")
public class MembershipController {
    private final MembershipService membershipService;
    private final MembershipHistoryService membershipHistoryService;

    @PostMapping("/subscribe")
    public MembershipResponseDTO subscribe(@Valid @RequestBody SubscribeRequestDTO subscribeRequestDTO){
        return membershipService.subscribe(subscribeRequestDTO);
    }

    @GetMapping("/{id}")
    public MembershipResponseDTO getMembership(@PathVariable Long id){
        return membershipService.getMembership(id);
    }

    @GetMapping("/{membershipId}/history")
    public List<MembershipHistoryResponseDTO> getMembershipHistory(@PathVariable Long membershipId){
        return membershipHistoryService.getMembershipHistory(membershipId);
    }

    @PostMapping("/{membershipId}/cancel")
    public MembershipResponseDTO cancelMembership(@PathVariable Long membershipId){
        return membershipService.cancelMembership(membershipId);
    }
}
