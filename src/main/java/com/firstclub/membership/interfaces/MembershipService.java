package com.firstclub.membership.interfaces;

import com.firstclub.membership.DTO.MembershipResponseDTO;
import com.firstclub.membership.DTO.SubscribeRequestDTO;
import com.firstclub.membership.DTO.UpgradeMembershipRequestDTO;

public interface MembershipService {
    MembershipResponseDTO subscribe(SubscribeRequestDTO subscribeRequestDTO);
    MembershipResponseDTO getMembership(Long id);
    MembershipResponseDTO cancelMembership(Long membershipId);
    MembershipResponseDTO upgradeMembership(Long membershipId, UpgradeMembershipRequestDTO upgradeMembershipRequestDTO);
    MembershipResponseDTO downgradeMembership(Long membershipId, UpgradeMembershipRequestDTO upgradeMembershipRequestDTO);
}
