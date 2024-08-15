package com.ecommerce.paymentservice.integration;

import com.ecommerce.paymentservice.modeldto.PaymentDTO;
import com.ecommerce.paymentservice.modeldto.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceIntegrationImp implements OrderServiceIntegration {

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<String> login(Login login) {

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/login")
                        .build())
                .bodyValue(login)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(throwable -> {
                    System.err.println("Original error: " + throwable.getMessage());
                    throwable.printStackTrace();
                })
                .onErrorMap(throwable -> new RuntimeException("Failed to login: " + throwable.getMessage(), throwable));
    }

    /**
     * Check if user is found
     */
    public Mono<PaymentDTO> isUserFound(Long userId, String authToken){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getUserById")
                        .queryParam("id", userId)
                        .build())
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(PaymentDTO.class);
    }
}
