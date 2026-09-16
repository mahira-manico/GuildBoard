package com.laplateforme.guildboard.application.dto;

import com.laplateforme.guildboard.application.entity.Difficulty;
import com.laplateforme.guildboard.application.entity.Status;

public record QuestAnswerDTO(Long id, String title, String description, Difficulty difficulty, Integer requiredLevel, Integer goldReward, Integer xpReward, Status status){}
