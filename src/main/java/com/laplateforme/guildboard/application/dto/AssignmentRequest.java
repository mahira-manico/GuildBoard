package com.laplateforme.guildboard.application.dto;

import jakarta.validation.constraints.NotNull;

public record AssignmentRequest(
        @NotNull(message = "Adventurer id is required")
        Long adventurer_id) {
}
