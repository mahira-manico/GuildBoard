package com.laplateforme.guildboard.application.dto;
import com.laplateforme.guildboard.application.entity.CharacterClass;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AdventurerRequestDTO {

    @NotBlank(message = "Name field cannot be blank")
    @Size(min = 2, max = 50)
    private String name;

    @NotNull(message = "Character class must be chosen!")
    @Enumerated(EnumType.STRING)
    private CharacterClass characterClass;

    // Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     // Setters
    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }
}
