package com.firstclub.membership.interfaces;

import com.firstclub.membership.DTO.MembershipResponseDTO;
import com.firstclub.membership.DTO.SubscribeRequestDTO;

public interface MembershipService {
    MembershipResponseDTO subscribe(SubscribeRequestDTO subscribeRequestDTO);
    MembershipResponseDTO getMembership(Long id);
}
