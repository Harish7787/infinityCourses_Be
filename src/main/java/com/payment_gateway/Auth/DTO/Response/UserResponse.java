package com.payment_gateway.Auth.DTO.Response;

import com.payment_gateway.Auth.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Integer id;

    private String name;

    private String email;

    private Role role;
}