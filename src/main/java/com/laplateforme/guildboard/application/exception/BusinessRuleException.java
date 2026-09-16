package com.laplateforme.guildboard.application.exception;

//Class for Business rules infractions, inherit of runtimeException
public class BusinessRuleException extends RuntimeException{
    private final String code;
    private String message;

    public BusinessRuleException(String code, String message){
        super(message);
        this.code=code;
    }

    public String getCode(){
        return code;
    }
}
