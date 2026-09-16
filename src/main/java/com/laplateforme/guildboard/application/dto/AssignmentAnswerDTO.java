package com.laplateforme.guildboard.application.dto;
import java.time.LocalDateTime;

public record AssignmentAnswerDTO(Long id, Long adventurer_id, Long quest, LocalDateTime assigned_at, LocalDateTime completed_at) {
}
