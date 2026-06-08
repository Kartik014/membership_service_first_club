package com.firstclub.membership.services;

import com.firstclub.membership.DTO.MembershipPlanResponseDTO;
import com.firstclub.membership.interfaces.MembershipPlanService;
import com.firstclub.membership.repository.MembershipPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipPlanServiceImpl implements MembershipPlanService {
    private final MembershipPlanRepository membershipPlanRepository;

    @Override
    public List<MembershipPlanResponseDTO> getAllPlans() {
        return membershipPlanRepository
                .findAll()
                .stream()
                .map(plan -> new MembershipPlanResponseDTO(
                        plan.getId(),
                        plan.getName(),
                        plan.getDurationDays(),
                        plan.getPrice()
                ))
                .toList();
    }
}
