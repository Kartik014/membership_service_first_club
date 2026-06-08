package com.firstclub.membership.DTO;

import java.util.UUID;

public record UserResponseDTO (
        UUID userCode,
        String name,
        String email,
        String cohort,
        Long id
){
}
