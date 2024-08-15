package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.model.Payment;

import java.util.List;


public interface PaymentService {

    /**
     * Get all payments
     */
     public List<Payment> getAllPayments();

    /**
     * Get payment by Id
     */
    public Payment getPaymentById(Long id);

    /**
     * Create payment
     */
    public Payment createPayment(Payment payment);

    /**
     * Update payment
     */
    public Payment updatePayment(Long id, Payment payment);

    /**
     * Delete payment
     */
    public void deletePayment(Long id);
}
