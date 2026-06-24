package com.payment_gateway.Payment.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {

    private String orderId;
    private Integer amount;
    private String currency;
}