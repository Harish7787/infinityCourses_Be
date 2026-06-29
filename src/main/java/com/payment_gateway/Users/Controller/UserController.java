package com.payment_gateway.Users.Controller;

import com.payment_gateway.Auth.Entity.User;
import com.payment_gateway.Auth.Repository.UserRepository;
import com.payment_gateway.Users.DTO.CreateUserRequest;
import com.payment_gateway.Auth.DTO.Response.UserResponse;
import com.payment_gateway.Users.DTO.Update.UpdateUserRequest;
import com.payment_gateway.Users.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserRepository userRepository;
    @PostMapping
    public ResponseEntity<?> create(
            @Valid
            @RequestBody CreateUserRequest request) {

        return ResponseEntity.ok(
                service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable int id) {

        return ResponseEntity.ok(
                service.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return ResponseEntity.ok(
                service.getAll(page, size));
    }

    @GetMapping("/deleted")
    public ResponseEntity<?> getAllDeleted(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return ResponseEntity.ok(
                service.getAllDeleted(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable int id,
            @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(
                service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable int id) {

        service.delete(id);

        return ResponseEntity.ok(
                "User deleted");
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<?> restore(
            @PathVariable int id) {

        service.restore(id);

        return ResponseEntity.ok(
                "User restored");
    }
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user);
    }
}
