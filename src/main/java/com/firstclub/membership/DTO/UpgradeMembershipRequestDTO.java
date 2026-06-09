package com.firstclub.membership.DTO;

import jakarta.validation.constraints.NotNull;

public record UpgradeMembershipRequestDTO(
        @NotNull
        Long newTierId
) {
}
