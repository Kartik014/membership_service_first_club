package com.firstclub.membership.services;

import com.firstclub.membership.DTO.TiersResponseDTO;
import com.firstclub.membership.entity.Tier;
import com.firstclub.membership.interfaces.TiersService;
import com.firstclub.membership.repository.TiersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TiersServiceImpl implements TiersService {
    private final TiersRepository tiersRepository;

    private TiersResponseDTO mapToResponse(Tier tier){
        return new TiersResponseDTO(
                tier.getId(),
                tier.getName(),
                tier.getPriority(),
                tier.getActive()
        );
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional(readOnly = true)
    public TiersResponseDTO getTier(Long id){
        if(!tiersRepository.existsById(id)){
            throw new RuntimeException("Tier with id " + id + " not found");
        }

        Tier tier = tiersRepository.findById(id).get();

        return mapToResponse(tier);
    }
}
