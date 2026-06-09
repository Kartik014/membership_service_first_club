package com.firstclub.membership.controllers;

import com.firstclub.membership.DTO.Responses.TiersResponseDTO;
import com.firstclub.membership.interfaces.TiersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tiers")
public class TiersController {
    private final TiersService tiersService;

    @GetMapping
    List<TiersResponseDTO> getAllTiers() {
        return tiersService.getAllTiers();
    }

    @GetMapping("/{id}")
    TiersResponseDTO getTier(@PathVariable Long id){
        return tiersService.getTier(id);
    }
}
