package com.payment_gateway.Auth.Service;


import com.payment_gateway.Auth.DTO.LoginRequest;
import com.payment_gateway.Auth.DTO.RegisterRequest;
import com.payment_gateway.Auth.DTO.Response.DataResponse;
import com.payment_gateway.Auth.DTO.Response.MessageResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

     ResponseEntity<MessageResponse> register(RegisterRequest request);
    ResponseEntity<?> login(LoginRequest request);
}