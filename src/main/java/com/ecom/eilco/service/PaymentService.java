package com.ecom.eilco.service;

import com.ecom.eilco.model.Order;
import com.ecom.eilco.model.Payment;
import com.ecom.eilco.repository.OrderRepository;
import com.ecom.eilco.repository.PaymentRepository;
import com.stripe.model.PaymentIntent;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StripeService stripeService;
    
    public PaymentIntent initiatePayment(long orderId, double amount) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));
            
        PaymentIntent intent = stripeService.createPaymentIntent(amount, "usd");
        
        Payment payment = new Payment();
        payment.setStripePaymentId(intent.getId());
        payment.setAmount(amount);
        payment.setOrder(order);
        
        paymentRepository.save(payment);
        
        return intent;
    }

    public void confirmPayment(String paymentIntentId) {
        Payment payment = paymentRepository.findByStripePaymentId(paymentIntentId)
            .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
            
        Order order = payment.getOrder();
        order.setStatus("PAID");
        order.setOrderDate(LocalDateTime.now());
        
        orderRepository.save(order); 
    }
}