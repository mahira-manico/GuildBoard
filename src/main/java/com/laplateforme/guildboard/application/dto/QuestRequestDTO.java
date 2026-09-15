package com.laplateforme.guildboard.application.dto;

import com.laplateforme.guildboard.application.entity.Difficulty;
import com.laplateforme.guildboard.application.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

public record QuestRequestDTO(
        @NotBlank(message = "Title can't be empty")
        @Size(min = 5, max=500, message = "5 characters minimum and 500 max!")
        String title,

        @NotBlank(message = "Description cannot be empty!")
        @Size(min = 10, max = 500, message = "10 characters minimum and 500 max!")
        String description,

        @NotNull(message = "difficulty must be chosen!")
        @Enumerated(EnumType.STRING)
        Difficulty difficulty,

        @Min(value = 1, message = "Level must be 1 minimum")
        Integer requiredLevel,

        @Min(value = 0, message = "Value must be chosen")
        Integer goldReward,

        @Min(value = 0, message = "Value must be chosen!")
        @Positive(message = "Value cannot be negative!")
        Integer xpReward,

        @NotNull(message = "Status must be chosen!")
        @Enumerated(EnumType.STRING)
        Status status
        ){}
