package com.ecommerce.paymentservice.integration;

import com.ecommerce.paymentservice.modeldto.OrderResponseDTO;
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

    /**
     * Is order found
     */
    public Mono<Boolean> isOrderFound(String orderCode);

    /** Get order by order code
     *
     * @param orderCode
     * @return
     */
    public Mono<OrderResponseDTO> getOrderByCode(String orderCode);
}
