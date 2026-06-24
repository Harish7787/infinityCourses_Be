package com.payment_gateway.Payment.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentVerifyRequest {

    private String courseName;

    private String userName;

    private String email;

    private String phone;

    private Double amount;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;
}