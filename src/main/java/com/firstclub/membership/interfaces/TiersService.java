package com.firstclub.membership.interfaces;

import com.firstclub.membership.DTO.Responses.TierRefreshResponseDTO;
import com.firstclub.membership.DTO.Responses.TiersResponseDTO;
import com.firstclub.membership.entity.Tier;

import java.util.List;

public interface TiersService {
    List<TiersResponseDTO> getAllTiers();
    TiersResponseDTO getTier(Long id);
    Tier evaluateTier(Long userId);
    TierRefreshResponseDTO refreshUserTier(Long membershipId);
}
