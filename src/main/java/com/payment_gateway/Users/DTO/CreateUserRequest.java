package com.payment_gateway.Users.DTO;

import com.payment_gateway.Auth.Entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;

    private String avatar;
    private String phone;

    private Role role;
}