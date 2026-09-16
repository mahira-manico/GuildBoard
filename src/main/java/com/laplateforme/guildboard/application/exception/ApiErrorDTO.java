package com.laplateforme.guildboard.application.exception;

public record ApiErrorDTO(Integer status, String code, String message) {
}
