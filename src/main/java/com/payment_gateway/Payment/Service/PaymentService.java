package com.payment_gateway.Payment.Service;

import com.payment_gateway.Payment.DTO.OrderRequest;
import com.payment_gateway.Payment.DTO.PaymentVerifyRequest;
import com.razorpay.Order;
public interface PaymentService {

    Order createOrder(OrderRequest request) throws Exception;
    void savePayment(PaymentVerifyRequest request);;
}