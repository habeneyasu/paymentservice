package com.ecommerce.paymentservice.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Settings {
    public String generateReference() {
        // Timestamp + UUID for uniqueness
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);  // Shortened UUID
        return "TXN-" + timestamp + "-" + uuidPart;  // TXN-20240910153012-1234abcd
    }
}

