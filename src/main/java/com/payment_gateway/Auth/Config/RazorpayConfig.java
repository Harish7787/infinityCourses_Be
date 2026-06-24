package com.payment_gateway.Auth.Config;

import com.razorpay.RazorpayClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RazorpayConfig {

    @Value("${razorpay.key.id}")
    private String razorpayKey;

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    @Bean
    public RazorpayClient razorpayClient() throws Exception {

        return new RazorpayClient(
                razorpayKey,
                razorpaySecret
        );
    }
}