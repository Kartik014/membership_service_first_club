package com.firstclub.membership.DTO.Responses;

import com.firstclub.membership.enums.MembershipStatus;
import java.time.LocalDateTime;

public record MembershipResponseDTO(
        Long membershipId,
        Long userId,
        String userName,
        String planName,
        String tierName,
        MembershipStatus status,
        LocalDateTime startDate,
        LocalDateTime expiryDate
) {
}
