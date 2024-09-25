package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.model.Payment;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;
import java.util.Optional;


public interface PaymentService {

    /**
     * Get all payments
     */
     public List<Payment> getAllPayments();

    /**
     * Get payment by Id
     */
    public Optional<Payment> getPaymentById(Long id);

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
    public boolean deletePayment(Long id);

    /**
     * Kafka message consumer
     */
    public void consumeOrder(ConsumerRecord<String,String> message);

    public String generateTransactionKey();
}
