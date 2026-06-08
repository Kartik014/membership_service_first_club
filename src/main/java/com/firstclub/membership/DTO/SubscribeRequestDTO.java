package com.firstclub.membership.DTO;

import jakarta.validation.constraints.NotNull;

public record SubscribeRequestDTO(
        @NotNull
        Long userId,

        @NotNull
        Long planId,

        @NotNull
        Long tierId,

        boolean autoRenew
) {
}
