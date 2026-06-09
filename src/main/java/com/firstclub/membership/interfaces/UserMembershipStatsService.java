package com.firstclub.membership.interfaces;

import com.firstclub.membership.DTO.Requests.UpdateUserMembershipStatsRequestDTO;
import com.firstclub.membership.DTO.Responses.UserMembershipStatsResponseDTO;

public interface UserMembershipStatsService {
    UserMembershipStatsResponseDTO addUserStats(Long userId, UpdateUserMembershipStatsRequestDTO updateUserMembershipStatsRequestDTO);
}
