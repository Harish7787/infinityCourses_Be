package com.payment_gateway.Users.Service;

import com.payment_gateway.Users.DTO.CreateUserRequest;
import com.payment_gateway.Users.DTO.Response.UserResponse;
import com.payment_gateway.Users.DTO.Update.UpdateUserRequest;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponse create(CreateUserRequest request);

    UserResponse getById(int id);

    Page<UserResponse> getAll(
            int page,
            int size);

    UserResponse update(
            int id,
            UpdateUserRequest request);

    void delete(int id);

    void restore(int id);
}