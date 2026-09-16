package com.laplateforme.guildboard.application.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Generalized Annotation for REST errors management
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Use Business rules class and handle 422 errors
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiErrorDTO>BusinessRuleError(BusinessRuleException errors){
        ApiErrorDTO errorDTO=new ApiErrorDTO(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                errors.getCode(),
                errors.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(errorDTO);
    }

    //Use Ressources rules class and handle 404 errors
    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<ApiErrorDTO>RessourceNotFoundError(RessourceNotFoundException errors){
        ApiErrorDTO errorDTO=new ApiErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                errors.getCode(),
                errors.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    //Used in DTO and Controller by using @Valid annotation, handle 400 errors
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

    //Handle all Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO>ExceptionHandler(Exception exception){
        ApiErrorDTO errorDTO=new ApiErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERRROR",
                "Une erreur inattendu est survenu");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorDTO>MessageErrorsException(HttpMessageNotReadableException msg){
        ApiErrorDTO errorDTO=new ApiErrorDTO(
                400,
                "BAD_REQUEST",
                "Le corps de la requête est mal formulé ou une valeur est incorrecte"
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
}