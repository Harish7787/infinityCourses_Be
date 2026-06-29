package com.payment_gateway.Users.DTO.Response;

import com.payment_gateway.Auth.Entity.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {

    private Integer id;

    private String name;

    private String email;

    private String phone;

    private Role role;

    private String avatar;

    private Boolean active;
}