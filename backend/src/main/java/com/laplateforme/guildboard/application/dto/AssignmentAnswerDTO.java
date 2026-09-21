package com.laplateforme.guildboard.application.dto;
import java.time.LocalDateTime;
import com.laplateforme.guildboard.application.entity.Quest;


public record AssignmentAnswerDTO(Long id, Long adventurer_id, Quest quest, LocalDateTime assigned_at, LocalDateTime completed_at) {
}
