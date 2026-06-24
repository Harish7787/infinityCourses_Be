package com.payment_gateway.Auth.Service.Impl;

import com.payment_gateway.Auth.Config.JWT.JwtService;
import com.payment_gateway.Auth.DTO.LoginRequest;
import com.payment_gateway.Auth.DTO.RegisterRequest;
import com.payment_gateway.Auth.DTO.Response.DataResponse;
import com.payment_gateway.Auth.DTO.Response.LoginResponse;
import com.payment_gateway.Auth.DTO.Response.MessageResponse;
import com.payment_gateway.Auth.DTO.Response.UserResponse;
import com.payment_gateway.Auth.Entity.Role;
import com.payment_gateway.Auth.Entity.User;
import com.payment_gateway.Auth.Repository.UserRepository;
import com.payment_gateway.Auth.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public ResponseEntity<MessageResponse> register(RegisterRequest request) {

        Optional<User> existingUser =
                userRepository.findByEmail(request.getEmail());

        // Check Email
        if (existingUser.isPresent()) {

            return ResponseEntity.badRequest()
                    .body(
                            new MessageResponse(
                                    false,
                                    HttpStatus.BAD_REQUEST,
                                    "Email already exists"
                            )
                    );
        }

        // Create New User
        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        // Encrypt Password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.USER);

        // Save User
        userRepository.save(user);

        return ResponseEntity.ok(
                new MessageResponse(
                        true,
                        HttpStatus.OK,
                        "User registered successfully"
                )
        );
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {

        // Find User By Email
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        // 1. Email Not Found
        if (user == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new MessageResponse(
                                    false,
                                    HttpStatus.NOT_FOUND,
                                    "Email not found"
                            )
                    );
        }

        // 2. Invalid Password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new MessageResponse(
                                    false,
                                    HttpStatus.UNAUTHORIZED,
                                    "Invalid password"
                            )
                    );
        }
        String role = "ROLE_" + user.getRole().name();

// Generate JWT Token
        String token = jwtService.generateToken(
                user.getEmail(),
                role,
                user.getId()
        );

        // 4. User Response
        UserResponse userResponse =
                new UserResponse(

                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                );

        // 5. Login Response
        LoginResponse response =
                new LoginResponse(
                        token,
                        userResponse
                );

        // 6. Success Response
        return ResponseEntity.ok(

                new DataResponse<>(
                        true,
                        HttpStatus.OK,
                        "Login successful",
                        response
                )
        );
    }
}
