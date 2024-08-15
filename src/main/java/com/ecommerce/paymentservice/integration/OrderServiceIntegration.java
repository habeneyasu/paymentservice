package com.ecommerce.paymentservice.integration;

import com.ecommerce.paymentservice.modeldto.PaymentDTO;
import com.ecommerce.paymentservice.modeldto.Login;
import reactor.core.publisher.Mono;

public interface OrderServiceIntegration {

    /**
     * Consume the login API
     */
    public Mono<String> login(Login login);

    /**
     * Check if user is found
     */
    public Mono<PaymentDTO> isUserFound(Long userId, String authToken);
}
