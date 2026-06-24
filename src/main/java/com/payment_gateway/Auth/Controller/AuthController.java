package com.payment_gateway.Auth.Controller;

import com.payment_gateway.Auth.DTO.LoginRequest;
import com.payment_gateway.Auth.DTO.RegisterRequest;
import com.payment_gateway.Auth.DTO.Response.DataResponse;
import com.payment_gateway.Auth.DTO.Response.MessageResponse;
import com.payment_gateway.Auth.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}