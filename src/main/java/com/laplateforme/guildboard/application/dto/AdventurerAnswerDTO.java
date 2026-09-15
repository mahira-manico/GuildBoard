package com.laplateforme.guildboard.application.dto;
import com.laplateforme.guildboard.application.entity.CharacterClass;

public class AdventurerAnswerDTO {

    private Long id;
    private String name;
    private CharacterClass characterClass;
    private int level;
    private int xp;
    private int gold;

// Getters
public Long getId() {
    return id;
}

public String getName() {
    return name;
}

public CharacterClass getCharacterClass() {
    return characterClass;
}

public int getLevel() {
    return level;
}

public int getXp() {
    return xp;
}

public int getGold() {
    return gold;
}


// Setters
public void setId(Long id) {
    this.id = id;
}

public void setName(String name) {
    this.name = name;
}

public void setCharacterClass(CharacterClass characterClass) {
    this.characterClass = characterClass;
}

public void setLevel(int level) {
    this.level = level;
}

public void setXp(int xp) {
    this.xp = xp;
}

public void setGold(int gold) {
    this.gold = gold;
}
}
