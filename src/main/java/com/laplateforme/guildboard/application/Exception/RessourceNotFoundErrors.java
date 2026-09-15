package com.laplateforme.guildboard.application.Exception;

public class RessourceNotFoundErrors extends RuntimeException {

    private String code;
    private String message;

    public RessourceNotFoundErrors(String code, String message) {
        super(message);
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
