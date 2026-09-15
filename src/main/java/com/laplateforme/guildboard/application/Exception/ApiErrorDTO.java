package com.laplateforme.guildboard.application.Exception;

public record ApiErrorDTO(Integer status, String code, String message) {
}
