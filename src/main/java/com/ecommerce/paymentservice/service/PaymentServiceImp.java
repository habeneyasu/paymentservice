package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.util.ConfigSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImp implements PaymentService {

    ConfigSetting utilSetting=new ConfigSetting();

@Autowired
private PaymentRepository paymentRepository;

    /**
     * Get all payments
     */
    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }

    /**
     * Get payment by Id
     */
    public Payment getPaymentById(Long id){
        return paymentRepository.findById(id).orElse(null);
    }

    /**
     * Create payment
     */
    public Payment createPayment(Payment payment){

        payment.setOrderCode(utilSetting.generateOrderCode());

        return paymentRepository.save(payment);
    }

    /**
     * Update payment
     */
    public Payment updatePayment(Long id, Payment payment){
        Payment findPayment=getPaymentById(id);
        if(findPayment!=null){
            findPayment.setOrderCode(payment.getOrderCode());
            findPayment.setPaymentMethod(payment.getPaymentMethod());
            findPayment.setTransactionRef(payment.getTransactionRef());
            findPayment.setAmount(payment.getAmount());
            findPayment.setUpdatedAt(new Date());
            return paymentRepository.save(findPayment);
        }
        return null;
    }

    /**
     * Delete payment
     */
    public void deletePayment(Long id) {
        Payment findPayment = getPaymentById(id);
        if (findPayment != null) {
            paymentRepository.delete(findPayment);
        }
    }
}
