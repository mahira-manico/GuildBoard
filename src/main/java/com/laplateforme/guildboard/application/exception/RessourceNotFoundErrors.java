package com.laplateforme.guildboard.application.exception;

public class RessourceNotFoundErrors extends RuntimeException {

    private final String code;
    private String message;

    public RessourceNotFoundErrors(String code, String message) {
        super(message);
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
