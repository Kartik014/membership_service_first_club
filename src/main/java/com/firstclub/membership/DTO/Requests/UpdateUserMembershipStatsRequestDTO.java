package com.firstclub.membership.DTO.Requests;

import java.math.BigDecimal;

public record UpdateUserMembershipStatsRequestDTO(
        Integer totalOrders,
        Integer monthlyOrders,
        BigDecimal monthlySpend
) {
}
