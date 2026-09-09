package com.laplateforme.guildboard.application.dto;

import com.laplateforme.guildboard.application.entity.Adventurer;
import com.laplateforme.guildboard.application.entity.Quest;

import java.time.LocalDateTime;

public record AssigmentResponse(Long id, Adventurer adventurer, Quest quest, LocalDateTime assigned_at, LocalDateTime completed_at) {
}
