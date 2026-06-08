package com.firstclub.membership.interfaces;

import com.firstclub.membership.DTO.MembershipPlanResponseDTO;
import java.util.List;

public interface MembershipPlanService {
    List<MembershipPlanResponseDTO> getAllPlans();
    MembershipPlanResponseDTO getPlan(Long id);
}
