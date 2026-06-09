package com.firstclub.membership.DTO;

import com.firstclub.membership.enums.MembershipAction;
import java.time.LocalDateTime;

public record MembershipHistoryResponseDTO(
        MembershipAction action,
        String oldTier,
        String newTier,
        LocalDateTime createdAt
) {
}
