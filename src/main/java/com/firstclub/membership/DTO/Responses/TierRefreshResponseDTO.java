package com.firstclub.membership.DTO.Responses;

public record TierRefreshResponseDTO(
        Long membershipId,
        String oldTier,
        String newTier,
        String action
) {
}
