package com.ecommerce.orderservice;

import com.ecommerce.paymentservice.PaymentServiceApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PaymentServiceApplication.class)
@AutoConfigureMockMvc
class PaymentServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testOrder() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/payments/helloWorld"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello world."));
	}


	@Test
	void contextLoads() {
	}

}
