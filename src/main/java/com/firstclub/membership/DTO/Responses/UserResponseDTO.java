package com.firstclub.membership.DTO.Responses;

import com.firstclub.membership.enums.UserCohort;

public record UserResponseDTO (
        String name,
        String email,
        UserCohort cohort,
        Long id
){
}
