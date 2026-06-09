package com.firstclub.membership.DTO.Responses;

import java.math.BigDecimal;

public record UserMembershipStatsResponseDTO(
        Long userId,
        Integer totalOrders,
        Integer monthlyOrders,
        BigDecimal totalSpend
) {
}
