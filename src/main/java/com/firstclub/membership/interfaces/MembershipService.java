package com.firstclub.membership.interfaces;

import com.firstclub.membership.DTO.Responses.MembershipResponseDTO;
import com.firstclub.membership.DTO.Requests.SubscribeRequestDTO;
import com.firstclub.membership.DTO.Requests.UpgradeMembershipRequestDTO;

public interface MembershipService {
    MembershipResponseDTO subscribe(SubscribeRequestDTO subscribeRequestDTO);
    MembershipResponseDTO getMembership(Long id);
    MembershipResponseDTO cancelMembership(Long membershipId);
    MembershipResponseDTO upgradeMembership(Long membershipId, UpgradeMembershipRequestDTO upgradeMembershipRequestDTO);
    MembershipResponseDTO downgradeMembership(Long membershipId, UpgradeMembershipRequestDTO upgradeMembershipRequestDTO);
}
