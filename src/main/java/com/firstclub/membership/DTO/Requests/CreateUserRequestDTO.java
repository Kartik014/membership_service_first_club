package com.firstclub.membership.DTO.Requests;

import com.firstclub.membership.enums.UserCohort;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid Email")
        String email,

        UserCohort cohort
) {
}
