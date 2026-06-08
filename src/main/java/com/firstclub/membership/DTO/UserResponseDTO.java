package com.firstclub.membership.DTO;

public record UserResponseDTO (
        String name,
        String email,
        String cohort,
        Long id
){
}
