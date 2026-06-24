package com.payment_gateway.Payment.Service.Impl;


import com.payment_gateway.Payment.DTO.OrderRequest;
import com.payment_gateway.Payment.DTO.PaymentVerifyRequest;
import com.payment_gateway.Payment.Entity.Booking;
import com.payment_gateway.Payment.Repository.BookingRepository;
import com.payment_gateway.Payment.Service.PaymentService;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.razorpay.Order;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl
        implements PaymentService {

    private final RazorpayClient razorpayClient;
    private final BookingRepository bookingRepository;
    @Override
    public Order createOrder(OrderRequest request) throws Exception {

        JSONObject options = new JSONObject();

        options.put(
                "amount",
                request.getAmount() * 100
        );

        options.put("currency", "INR");

        options.put(
                "receipt",
                "txn_" + System.currentTimeMillis()
        );

        return razorpayClient
                .orders
                .create(options);
    }
    @Override
    public void savePayment(PaymentVerifyRequest request) {

        if (bookingRepository.existsByRazorpayPaymentId(
                request.getRazorpayPaymentId())) {

            return;
        }

        System.out.println("SAVE METHOD CALLED");

        Booking booking = Booking.builder()
                .courseName(request.getCourseName())
                .userName(request.getUserName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .amount(request.getAmount())
                .paymentStatus("SUCCESS")
                .razorpayPaymentId(request.getRazorpayPaymentId())
                .razorpayOrderId(request.getRazorpayOrderId())
                .createdAt(LocalDateTime.now())
                .build();

        bookingRepository.save(booking);

        System.out.println("BOOKING SAVED ID = " + booking.getId());
    }
}