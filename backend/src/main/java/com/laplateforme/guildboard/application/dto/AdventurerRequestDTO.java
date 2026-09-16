package com.laplateforme.guildboard.application.dto;
import com.laplateforme.guildboard.application.entity.CharacterClass;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdventurerRequestDTO(

    @NotBlank(message = "Le prénom est obligatoire!")
    @Size(min = 2, max = 50, message = "Le prénom doit faire entre 2 et 50 caractères!")
    String name,

    @NotNull(message = "Une classe de personnage doit être choisi!")
    CharacterClass characterClass
    ) {

}

