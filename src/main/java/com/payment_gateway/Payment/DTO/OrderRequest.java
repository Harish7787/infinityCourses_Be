package com.payment_gateway.Payment.DTO;

import lombok.Data;

@Data
public class OrderRequest {

    private String courseName;

    private String userName;

    private String email;

    private String phone;

    private Double amount;
}