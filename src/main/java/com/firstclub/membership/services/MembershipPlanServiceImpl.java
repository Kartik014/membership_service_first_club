package com.firstclub.membership.services;

import com.firstclub.membership.DTO.MembershipPlanResponseDTO;
import com.firstclub.membership.entity.MembershipPlan;
import com.firstclub.membership.interfaces.MembershipPlanService;
import com.firstclub.membership.repository.MembershipPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipPlanServiceImpl implements MembershipPlanService {
    private final MembershipPlanRepository membershipPlanRepository;

    private MembershipPlanResponseDTO mapToResponse(MembershipPlan plan){
        return new MembershipPlanResponseDTO(
                plan.getId(),
                plan.getName(),
                plan.getDurationDays(),
                plan.getPrice()
        );
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional(readOnly = true)
    public MembershipPlanResponseDTO getPlan(Long id){
        if(!membershipPlanRepository.existsById(id)){
            throw new RuntimeException("Membership plan with id " + id + " not found");
        }

        MembershipPlan plan = membershipPlanRepository.findById(id).get();

        return mapToResponse(plan);
    }
}
