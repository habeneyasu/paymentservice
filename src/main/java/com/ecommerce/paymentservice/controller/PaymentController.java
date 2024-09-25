
package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.integration.OrderServiceIntegration;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.modeldto.PaymentDTO;
import com.ecommerce.paymentservice.modeldto.Login;
import com.ecommerce.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderServiceIntegration orderServiceIntegration;

    public PaymentController(PaymentService paymentService,OrderServiceIntegration orderServiceIntegration){
        this.paymentService=paymentService;
        this.orderServiceIntegration=orderServiceIntegration;
    }

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @GetMapping("/helloWorld")
    public ResponseEntity<String> helloWorld() {
        logger.info("Hello World test message.");
        return ResponseEntity
                .status(HttpStatus.OK)  // Set the status code to 200 OK
                .body("Hello world.");  // Set the body of the response
    }

    /**
     * Get all payments
     */
    public ResponseEntity<List<Payment>> getAllPayments() {
        try {
            List<Payment> payments = paymentService.getAllPayments();
            if (payments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            logger.error("Error fetching payments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * Get payment by Id
     */
    @GetMapping("/getPaymentById")
    public ResponseEntity<?> getPaymentById(@RequestParam("id") Long id) {
        try {
            Optional<Payment> paymentOptional = paymentService.getPaymentById(id);

            if (paymentOptional.isPresent()) {
                return ResponseEntity.ok(paymentOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Payment not found for ID: " + id);
            }
        } catch (RuntimeException e) {
            logger.error("Error fetching payment with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching payment.");
        }
    }

    /**
     * Create payment
     */
    @PostMapping("/createPayment")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        try {
            if (payment == null) {
                return ResponseEntity.badRequest().body(null);
            }
            Payment createdPayment = paymentService.createPayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
        } catch (Exception e) {
            logger.error("Error creating payment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Update payment
     */
    @PutMapping("/updatePayment/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable("id") Long id, @RequestBody Payment payment) {
        try {
            if (id == null || payment == null) {
                return ResponseEntity.badRequest().body(null);
            }
            Payment updatedPayment = paymentService.updatePayment(id, payment);

            if (updatedPayment != null) {
                return ResponseEntity.ok(updatedPayment);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (Exception e) {
            logger.error("Error updating payment with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * Delete payment
     */
    @DeleteMapping("/deletePayment/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") Long id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().build();
            }
            boolean isDeleted = paymentService.deletePayment(id);

            if (isDeleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("Error deleting payment with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> loginUser(@RequestBody Login login) {
        return orderServiceIntegration.login(login)
                .map(response -> {
                    return ResponseEntity.ok(response);
                })
                .onErrorResume(e -> {
                    logger.error("Login failed for user: {}", login.getUsername(), e);
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Login failed. Please check your credentials."));
                });
    }

    @GetMapping("/getUser")
    public Mono<PaymentDTO> getUser(@RequestParam("userId") Long userId ) {
        String authToken="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6W10sInN1YiI6ImthbGVidGFrZWxlIiwiaWF0IjoxNzIxMjE1Mzc5LCJleHAiOjE3MjEyMTcxNzl9.riY0PIGA_qEx9q0i6iwd4uhu7zNTwaKH2JlmA9atNEg";
        return orderServiceIntegration.isUserFound(userId,authToken);
    }

    /**
     * Get order by order code
     *
     * @param orderCode
     * @return
     */
    @GetMapping("/findOrderByCode")
    public Mono<ResponseEntity<String>> findOrderByCode(@RequestParam("order_code") String orderCode) {
        return orderServiceIntegration.isOrderFound(orderCode)
                .map(isFound -> {
                    if (isFound) {
                        return ResponseEntity.ok("Order found with code: " + orderCode);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Order not found with code: " + orderCode);
                    }
                })
                .onErrorResume(e -> {
                    // Log the error
                    logger.error("Error occurred while finding order with code: {}", orderCode, e);
                    // Return a 500 Internal Server Error response
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error occurred while finding the order."));
                });
    }


}