package com.payment_gateway.Payment.Repository;

import com.payment_gateway.Payment.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    boolean existsByRazorpayPaymentId(String razorpayPaymentId);
}
