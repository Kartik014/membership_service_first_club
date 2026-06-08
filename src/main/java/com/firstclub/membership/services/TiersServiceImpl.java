package com.firstclub.membership.services;

import com.firstclub.membership.DTO.TiersResponseDTO;
import com.firstclub.membership.interfaces.TiersService;
import com.firstclub.membership.repository.TiersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TiersServiceImpl implements TiersService {
    private final TiersRepository tiersRepository;

    @Override
    public List<TiersResponseDTO> getAllTiers() {
        return tiersRepository
                .findAll()
                .stream()
                .map(tier -> new TiersResponseDTO(
                        tier.getId(),
                        tier.getName(),
                        tier.getPriority(),
                        tier.getActive()
                ))
                .toList();
    }
}
