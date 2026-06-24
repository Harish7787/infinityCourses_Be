package com.payment_gateway.Auth.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class MessageResponse {

    private boolean success;

    private HttpStatus httpStatus;

    private String message;
}