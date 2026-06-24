package com.payment_gateway.Auth.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class DataResponse<T> {
    private boolean success;
    private HttpStatus status ;
    private String message;
    private T Data;

}
