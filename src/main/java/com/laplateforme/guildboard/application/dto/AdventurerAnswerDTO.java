package com.laplateforme.guildboard.application.dto;
import com.laplateforme.guildboard.application.entity.CharacterClass;

public record AdventurerAnswerDTO(Long id, String name, CharacterClass characterClass, Integer level, Integer xp, Integer gold){}
