package com.laplateforme.guildboard.application.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Adventurer {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(unique = true, length=50, nullable = false)
    @Size(min=2, max=50)
    private String name;

    @Enumerated(EnumType.STRING)
    private CharacterClass characterClass;

    @Size(min=1)
    private Integer level;

    @Size(min=0)
    private Integer xp;

    @Size(min=0)
    private Integer gold;

    protected Adventurer(){}

    public Adventurer(String name, CharacterClass characterClass){
        this.name=name;
        this.characterClass=characterClass;
        this.level=1;
        this.xp=0;
        this.gold=0;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public CharacterClass getCharacterClass(){
        return characterClass;
    }

    public int getLevel(){
        return level;
    }

    public int getXp(){
        return xp;
    }

    public int getGold(){
        return gold;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }
}


