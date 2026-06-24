package com.payment_gateway.Course.exception;
import com.payment_gateway.Course.DTO.Response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(
            ResourceNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new MessageResponse(
                                false,
                                HttpStatus.NOT_FOUND,
                                ex.getMessage()
                        )
                );
    }

    // Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(
            BadRequestException ex) {

        return ResponseEntity.badRequest()
                .body(
                        new MessageResponse(
                                false,
                                HttpStatus.BAD_REQUEST,
                                ex.getMessage()
                        )
                );
    }

    // Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Validation Failed");

        return ResponseEntity.badRequest()
                .body(
                        new MessageResponse(
                                false,
                                HttpStatus.BAD_REQUEST,
                                message
                        )
                );
    }

    // All Unexpected Errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(
            Exception ex) {

        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new MessageResponse(
                                false,
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ex.getMessage()
                        )
                );
    }
}