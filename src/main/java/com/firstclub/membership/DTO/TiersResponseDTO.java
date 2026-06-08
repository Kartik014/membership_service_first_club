package com.firstclub.membership.DTO;

public record TiersResponseDTO (
        Long id,
        String name,
        Integer priority,
        Boolean active
) {
}
