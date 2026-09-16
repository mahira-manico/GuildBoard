package com.laplateforme.guildboard.application.dto;

import com.laplateforme.guildboard.application.entity.Difficulty;
import com.laplateforme.guildboard.application.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

public record QuestRequestDTO(
        @NotBlank(message = "Le titre est obligatoire!")
        @Size(min = 5, max=500, message = "Le taille du titre doit être comprise entre 5 et 500 caractères!")
        String title,

        @NotBlank(message = "Une description est obligatoire!")
        @Size(min = 10, max = 500, message = "La taille de la description doit être comprise entre 10 et 500 caractères!")
        String description,

        @NotNull(message = "La difficulté est obligatoire!")
        @Enumerated(EnumType.STRING)
        Difficulty difficulty,

        @Min(value = 1, message = "Le niveau doit être de 1 minimum")
        Integer requiredLevel,

        @Min(value = 0, message = "L'or est obligatoire!")
        Integer goldReward,

        @Min(value = 0, message = "L'XP est obligatoire!")
        @Positive(message = "La valeur de l'XP ne doit pas être négative!")
        Integer xpReward,

        @NotNull(message = "Le status est obligatoire!")
        @Enumerated(EnumType.STRING)
        Status status
        ){}
