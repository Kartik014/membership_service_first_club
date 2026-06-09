package com.firstclub.membership.controllers;

import com.firstclub.membership.DTO.Requests.SubscribeRequestDTO;
import com.firstclub.membership.DTO.Requests.UpgradeMembershipRequestDTO;
import com.firstclub.membership.DTO.Responses.MembershipHistoryResponseDTO;
import com.firstclub.membership.DTO.Responses.MembershipResponseDTO;
import com.firstclub.membership.DTO.Responses.TierRefreshResponseDTO;
import com.firstclub.membership.interfaces.MembershipHistoryService;
import com.firstclub.membership.interfaces.MembershipService;
import com.firstclub.membership.interfaces.TiersService;
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
    private final TiersService tiersService;

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

    @PatchMapping("/{membershipId}/upgrade")
    public MembershipResponseDTO upgradeMembership(@PathVariable Long membershipId, @Valid @RequestBody UpgradeMembershipRequestDTO upgradeMembershipRequestDTO){
        return membershipService.upgradeMembership(membershipId, upgradeMembershipRequestDTO);
    }

    @PatchMapping("/{membershipId}/downgrade")
    public MembershipResponseDTO downgradeMembership(@PathVariable Long membershipId, @Valid @RequestBody UpgradeMembershipRequestDTO upgradeMembershipRequestDTO){
        return membershipService.downgradeMembership(membershipId, upgradeMembershipRequestDTO);
    }

    @PostMapping("/{membershipId}/refresh-tier")
    public TierRefreshResponseDTO refreshUserTier(@PathVariable Long membershipId){
        return tiersService.refreshUserTier(membershipId);
    }
}
