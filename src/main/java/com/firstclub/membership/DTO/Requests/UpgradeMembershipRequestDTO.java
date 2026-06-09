package com.firstclub.membership.DTO.Requests;

import jakarta.validation.constraints.NotNull;

public record UpgradeMembershipRequestDTO(
        @NotNull
        Long newTierId
) {
}
