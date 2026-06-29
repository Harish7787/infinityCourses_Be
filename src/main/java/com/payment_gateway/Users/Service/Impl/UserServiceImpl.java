package com.payment_gateway.Users.Service.Impl;

import com.payment_gateway.Auth.Entity.Role;
import com.payment_gateway.Auth.Entity.User;
import com.payment_gateway.Auth.Repository.UserRepository;
import com.payment_gateway.Users.DTO.CreateUserRequest;
import com.payment_gateway.Users.DTO.Response.UserResponse;
import com.payment_gateway.Users.DTO.Update.UpdateUserRequest;
import com.payment_gateway.Users.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    @Override
    public UserResponse create(
            CreateUserRequest request) {

        Role role = request.getRole() != null
                ? request.getRole()
                : Role.USER;

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(
                        encoder.encode(
                                request.getPassword()))
                .role(role)
                .avatar(request.getAvatar())
                .active(true)
                .build();

        repository.save(user);

        return map(user);
    }

    @Override
    public UserResponse getById(int id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        return map(user);
    }

    @Override
    public Page<UserResponse> getAll(
            int page,
            int size) {

        return repository.findByActiveTrue(
                        PageRequest.of(page, size))
                .map(this::map);
    }

    @Override
    public Page<UserResponse> getAllDeleted(
            int page,
            int size) {

        return repository.findByActiveFalse(
                        PageRequest.of(page, size))
                .map(this::map);
    }

    @Override
    public UserResponse update(
            int id,
            UpdateUserRequest request) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setAvatar(request.getAvatar());
        user.setActive(request.isActive());
        repository.save(user);

        return map(user);
    }

    @Override
    public void delete(int id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        user.setActive(false);

        repository.save(user);
    }

    @Override
    public void restore(int id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        user.setActive(true);

        repository.save(user);
    }

    private UserResponse map(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .role(user.getRole())
                .active(user.getActive())
                .build();
    }
}