package com.ecom.eilco.repository;

import com.ecom.eilco.model.Payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByStripePaymentId(String stripePaymentId);
}
