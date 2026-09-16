package com.laplateforme.guildboard.application.exception;

//Error DTO to send a JSON formatted errors to client
public record ApiErrorDTO(Integer status, String code, String message) {
}
