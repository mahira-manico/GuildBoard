package com.laplateforme.guildboard.application.dto;
import com.laplateforme.guildboard.application.entity.CharacterClass;

public class AdventurerRequestDTO {

    private String name;
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
