package com.ecommerce.paymentservice.util;

import com.ecommerce.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.ecommerce.paymentservice.model.Payment;

public class ConfigSetting {

    @Autowired
    private PaymentRepository paymentRepository;
    public String generateOrderCode() {
        String prefix = "ORD-";
        String maxOrderCode = paymentRepository.findTopByOrderByOrderCodeDesc()
                .map(Payment::getOrderCode)
                .orElse(prefix + "000000");

        int currentMax = Integer.parseInt(maxOrderCode.replace(prefix, ""));
        int newOrderCodeInt = currentMax + 1;
        String newOrderCodeStr = String.format("%06d", newOrderCodeInt);

        return prefix + newOrderCodeStr;
    }
}
