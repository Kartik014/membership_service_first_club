package com.firstclub.membership.controllers;

import com.firstclub.membership.DTO.Responses.MembershipPlanResponseDTO;
import com.firstclub.membership.interfaces.MembershipPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor
public class MembershipPlanController {
    private final MembershipPlanService membershipPlanService;

    @GetMapping
    public List<MembershipPlanResponseDTO> getPlans(){
        return membershipPlanService.getAllPlans();
    }

    @GetMapping("/{id}")
    public MembershipPlanResponseDTO getPlan(@PathVariable Long id){
        return membershipPlanService.getPlan(id);
    }
}
