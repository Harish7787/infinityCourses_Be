package com.payment_gateway.Payment.Controller;

import com.payment_gateway.Payment.DTO.OrderRequest;
import com.payment_gateway.Payment.DTO.PaymentVerifyRequest;
import com.payment_gateway.Payment.DTO.Response.OrderResponse;
import com.payment_gateway.Payment.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.razorpay.Order;
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

//    @PostMapping("/create-order")
//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
//    public ResponseEntity<?> createOrder(
//            @RequestBody OrderRequest request
//    ) throws Exception {
//
//        Order order = paymentService.createOrder(request);
//
//        System.out.println("ORDER RESPONSE = " + order);
//        return ResponseEntity.ok(order);
//    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequest request
    ) throws Exception {

        Order order = paymentService.createOrder(request);

        OrderResponse response =
                new OrderResponse(
                        order.get("id"),
                        order.get("amount"),
                        order.get("currency")
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @RequestBody PaymentVerifyRequest request
    ) {

        System.out.println("VERIFY API HIT");
        System.out.println("ORDER ID = " + request.getRazorpayOrderId());
        System.out.println("PAYMENT ID = " + request.getRazorpayPaymentId());
        paymentService.savePayment(request);

        System.out.println("DATA SAVED");

        return ResponseEntity.ok("Saved Successfully");
    }
}
