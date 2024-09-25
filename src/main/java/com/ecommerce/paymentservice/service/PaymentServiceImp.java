package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.config.PaymentMethodEnum;
import com.ecommerce.paymentservice.controller.PaymentController;
import com.ecommerce.paymentservice.exception.ResourceNotFoundException;
import com.ecommerce.paymentservice.integration.OrderServiceIntegration;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.modeldto.OrderResponseDTO;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.util.ConfigSetting;
import jakarta.transaction.Transactional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import com.ecommerce.paymentservice.config.Settings;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImp implements PaymentService {

    ConfigSetting utilSetting=new ConfigSetting();

    private final PaymentRepository paymentRepository;
    private final OrderServiceIntegration orderServiceIntegration;
    private final KafkaTemplate<String, String> kafkaTemplate;

    Settings settings=new Settings();

    public PaymentServiceImp(PaymentRepository paymentRepository, OrderServiceIntegration orderServiceIntegration,KafkaTemplate<String, String> kafkaTemplate) {
        this.paymentRepository = paymentRepository;
        this.orderServiceIntegration = orderServiceIntegration;
        this.kafkaTemplate = kafkaTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private static final String ORDER_TOPIC = "order-topic";
    private static final String PAYMENT_TOPIC = "payment-event";
    /**
     * Get all payments
     */
    public List<Payment> getAllPayments(){
        try {
            return paymentRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching payments", e);
            throw new RuntimeException("Error fetching payments", e);
        }
    }

    /**
     * Get payment by Id
     */
    public Optional<Payment> getPaymentById(Long id){
        try {
            return paymentRepository.findById(id);
        } catch (Exception e) {
            // Log the exception
            logger.error("Error fetching payment with ID: {}", id, e);
            // Handle or throw the exception as needed
            throw new RuntimeException("Error fetching payment", e);
        }
    }


    /**
     * Create payment
     */
    public Payment createPayment(Payment payment){
        validatePayment(payment);
        try {
            Payment savedPayment = paymentRepository.save(payment);
            logger.info("Payment created successfully with  order code: {}", savedPayment.getOrderCode());
            return savedPayment;
        } catch (Exception e) {
            logger.error("Error creating payment", e);
            throw new RuntimeException("Error creating payment", e);
        }
    }

    /**
     * Update payment
     */
    @Transactional
    public Payment updatePayment(Long id, Payment payment){

        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for ID: " + id));

        existingPayment.setOrderCode(payment.getOrderCode());
        existingPayment.setPaymentMethod(payment.getPaymentMethod());
        existingPayment.setTransactionRef(payment.getTransactionRef());
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setUpdatedAt(new Date()); // Consider using LocalDateTime

        Payment updatedPayment = paymentRepository.save(existingPayment);
        logger.info("Payment updated successfully with order code: {}", updatedPayment.getOrderCode());
        return updatedPayment;
    }

    /**
     * Delete payment
     */
    @Transactional
    public boolean deletePayment(Long id) {
        try {
            Payment payment = paymentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Payment not found for ID: " + id));
            paymentRepository.delete(payment);

            logger.info("Payment deleted successfully with ID: {}", id);
            return true;
        } catch (ResourceNotFoundException e) {
            logger.error("Error deleting payment with ID: {}", id, e);
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while deleting payment with ID: {}", id, e);
            return false;
        }
    }

    //@KafkaListener(topics = ORDER_TOPIC,groupId = "payment-service", topicPartitions = @TopicPartition(topic = "order-topic",partitions = {"1","2"}))
    @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 3000, multiplier = 2))
    @KafkaListener(topics = ORDER_TOPIC, groupId = "payment-service")
    public void consumeOrder(ConsumerRecord<String, String> message) {
        try {
            String orderCode = message.value();
            OrderResponseDTO orderResponse = orderServiceIntegration.getOrderByCode(orderCode).block();

            if (orderResponse != null) {
                Payment payment = createPaymentFromOrderResponse(orderResponse);

                Payment savedPayment = paymentRepository.save(payment);
                logger.info("Payment created successfully with ID: {}", savedPayment.getId());

                kafkaTemplate.send(PAYMENT_TOPIC, generateTransactionKey(), settings.generateReference())
                        .whenComplete((sendResult, throwable) -> {
                            if (throwable != null) {
                                onFailure(throwable);
                            } else {
                                onSuccess(sendResult);
                            }
                        });
            } else {
                logger.warn("Order with code {} not found", orderCode);
            }

            logMessageDetails(message, orderResponse);

        } catch (Exception e) {
            logger.error("Error processing message with key: {}", message.key(), e);
        }
    }

    private Payment createPaymentFromOrderResponse(OrderResponseDTO orderResponse) {
        Payment payment = new Payment();
        payment.setOrderCode(orderResponse.getOrderCode());
        payment.setAmount(orderResponse.getTotalAmount());
        payment.setPaymentMethod(PaymentMethodEnum.CREDIT_CARD);
        payment.setTransactionRef(settings.generateReference());
        return payment;
    }

    private void logMessageDetails(ConsumerRecord<String, String> message, OrderResponseDTO orderResponse) {
        if (orderResponse != null) {
            logger.info("Order code       : {}", orderResponse.getOrderCode());
            logger.info("Status           : {}", orderResponse.getStatus());
            logger.info("Amount           : {}", orderResponse.getTotalAmount());
            logger.info("CreatedAt        : {}", orderResponse.getCreatedAt());
        }
        logger.info("Message key      : {}", message.key());
        logger.info("Message value    : {}", message.value());
        logger.info("Partition        : {}", message.partition());
        logger.info("Offset           : {}", message.offset());
    }
    public String generateTransactionKey(){
        return UUID.randomUUID().toString();
    }

    private void onSuccess(SendResult<String, String> sendResult) {
        logger.info("Received new meta data.\n"+ "Topic:{},Partition:{}",
                sendResult.getRecordMetadata().topic(),
                sendResult.getRecordMetadata().partition());
    }

    private void onFailure(Throwable throwable) {
        logger.info("There was an error sending the message.\n");
    }

    private void validatePayment(Payment payment) {
        if (payment.getOrderCode() == null || payment.getTransactionRef() == null) {
            throw new IllegalArgumentException("Payment order code and transaction ref cannot be null");
        }
    }
}
