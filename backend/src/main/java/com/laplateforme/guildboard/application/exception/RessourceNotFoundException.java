package com.laplateforme.guildboard.application.exception;

//Class handling missing ressources error, inherit of RunTimeException
public class RessourceNotFoundException extends RuntimeException {

    private final String code;
    private String message;

    public RessourceNotFoundException(String code, String message) {
        super(message);
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
