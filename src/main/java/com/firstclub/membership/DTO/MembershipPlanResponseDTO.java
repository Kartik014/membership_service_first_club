package com.firstclub.membership.DTO;

import java.math.BigDecimal;

public record MembershipPlanResponseDTO (
        Long id,
        String name,
        Integer durationDays,
        BigDecimal price
){
}
