package com.firstclub.membership.interfaces;

import com.firstclub.membership.DTO.TiersResponseDTO;
import java.util.List;

public interface TiersService {
    List<TiersResponseDTO> getAllTiers();
    TiersResponseDTO getTier(Long id);
}
