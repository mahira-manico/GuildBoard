package com.laplateforme.guildboard.application.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessRuleErrors.class)
    public ResponseEntity<ApiErrorDTO>BusinessRuleError(BusinessRuleErrors errors){
        ApiErrorDTO errorDTO=new ApiErrorDTO(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                errors.getCode(),
                errors.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(errorDTO);
    }

    @ExceptionHandler(RessourceNotFoundErrors.class)
    public ResponseEntity<ApiErrorDTO>RessourceNotFoundError(RessourceNotFoundErrors errors){
        ApiErrorDTO errorDTO=new ApiErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                errors.getCode(),
                errors.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO>ArgumentNotValidError(MethodArgumentNotValidException exception){

        String messageEx=exception.getBindingResult()
                .getFieldErrors().stream().findFirst()
                .map(err->err.getField()+":"+err.getDefaultMessage()).orElse("Donées invalides");

        ApiErrorDTO errorDTO=new ApiErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_FAILED",
                 messageEx);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO>ExceptionHandler(Exception exception){
        ApiErrorDTO errorDTO=new ApiErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERRROR",
                "Une erreur inattendu est survenu");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}