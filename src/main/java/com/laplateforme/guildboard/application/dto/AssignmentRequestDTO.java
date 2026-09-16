package com.laplateforme.guildboard.application.dto;

import jakarta.validation.constraints.NotNull;

public record AssignmentRequestDTO(
        @NotNull(message = "L'id de l'aventurier est requis!")
        Long adventurer_id) {
}
