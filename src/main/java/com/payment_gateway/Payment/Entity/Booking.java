package com.payment_gateway.Payment.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String courseName;

    private String userName;

    private String email;

    private String phone;

    private Double amount;

    private String paymentStatus;

    private String razorpayPaymentId;

    private String razorpayOrderId;

    private LocalDateTime createdAt;
}