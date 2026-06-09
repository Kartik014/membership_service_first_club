package com.firstclub.membership.interfaces;

import com.firstclub.membership.DTO.MembershipHistoryResponseDTO;
import java.util.List;

public interface MembershipHistoryService {
    List<MembershipHistoryResponseDTO> getMembershipHistory(Long membershipId);
}
