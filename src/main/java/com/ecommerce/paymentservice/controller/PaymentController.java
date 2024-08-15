
package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.integration.OrderServiceIntegration;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.modeldto.PaymentDTO;
import com.ecommerce.paymentservice.modeldto.Login;
import com.ecommerce.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/payments/")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);


    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderServiceIntegration orderServiceIntegration;
    @GetMapping("/helloWorld")
    public String HelloWorld(){

        System.out.println("Hello World test message.");
        return "Hello world.";
    }

    /**
     * Call API's using rest template testing
     */
    @GetMapping("/callApi")
    public String getMessage(){
        String uri="http://localhost:8182/api/v1/helloWorld";
        RestTemplate restTemplate=new RestTemplate();
        String result=restTemplate.getForObject(uri,String.class);
        return result;
    }

    /**
     * Get all payments
     */
    @GetMapping("/getAllPayments")
    public List<Payment> getAllPayments(){
        return paymentService.getAllPayments();
    }

    /**
     * Get payment by Id
     */
    @GetMapping("/getPaymentById")
    public Payment getPaymentById(@RequestParam("id") Long id){
        return paymentService.getPaymentById(id);
    }

    /**
     * Create payment
     */
    @PostMapping("/createPayment")
    public Payment createPayment(@RequestBody Payment payment){
        return paymentService.createPayment(payment);
    }



    /**
     * Update payment
     */
    @PutMapping("/updatePayment")
    public Payment updatePayment(@RequestParam("id") Long id,@RequestBody Payment payment){
        return paymentService.updatePayment(id,payment);
    }

    /**
     * Delete payment
     */
    @DeleteMapping("/deletePayment")
    public void deletePayment(@RequestParam("id") Long id){
         paymentService.deletePayment(id);
    }

    @PostMapping("/login")
    public Mono<String> loginUser(@RequestBody Login login) {



        log.info("This is an INFO log message");
      //  log.debug("This is a DEBUG log message");
      //  log.error("This is an ERROR log message");

        return orderServiceIntegration.login(login);
    }

    @GetMapping("/getUser")
    public Mono<PaymentDTO> getUser(@RequestParam("userId") Long userId ) {
        String authToken="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6W10sInN1YiI6ImthbGVidGFrZWxlIiwiaWF0IjoxNzIxMjE1Mzc5LCJleHAiOjE3MjEyMTcxNzl9.riY0PIGA_qEx9q0i6iwd4uhu7zNTwaKH2JlmA9atNEg";
        return orderServiceIntegration.isUserFound(userId,authToken);
    }
}