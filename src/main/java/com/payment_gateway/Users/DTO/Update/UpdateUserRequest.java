package com.payment_gateway.Users.DTO.Update;

import com.payment_gateway.Auth.Entity.Role;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String name;

    private String phone;

    private String email;

    private boolean active;
    private String avatar;
    private Role role;
}