package com.payment_gateway.Auth.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String avatar;

    @Column(unique = true)
    private String email;

    private String phone;

    private Boolean active = true;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
