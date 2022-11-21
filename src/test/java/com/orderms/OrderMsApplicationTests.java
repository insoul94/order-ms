package com.orderms;

import com.orderms.dao.ProductRepository;
import com.orderms.utils.TestData;
import com.orderms.dao.CustomerRepository;
import com.orderms.dao.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderMsApplicationTests {

	@Autowired private MockMvc mockMvc;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private ProductRepository productRepository;
	@Autowired private OrderRepository orderRepository;

	@AfterEach
	void deleteAllAfterEachTest() {
		customerRepository.deleteAll();
		productRepository.deleteAll();
		orderRepository.deleteAll();
	}



	@Test
	void Should_CreateCustomer_When_PostCustomer() throws Exception {
		mockMvc.perform(post("/customer")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestData.mockCustomerJson()))
				.andDo(print())
				.andExpect(status().isCreated());
	}

	@Test
	void Should_ReturnBadRequest_When_PostCustomer_Given_InvalidData() throws Exception {
		mockMvc.perform(post("/customer")
						.content(TestData.mockCustomerWithBlankRegCodeJson())
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}



	@Test
	void Should_CreateProduct_When_PostProduct() throws Exception {
		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestData.mockProductJson()))
				.andDo(print())
				.andExpect(status().isCreated());
	}

	@Test
	void Should_ReturnBadRequest_When_PostProduct_Given_InvalidData() throws Exception {
		mockMvc.perform((post("/product")
						.content(TestData.mockProductWithBlankSkuCodeJson())
						.contentType(MediaType.APPLICATION_JSON)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}



	@Test
	void Should_CreateOrder_When_PostOrder() throws Exception {
		customerRepository.save(TestData.mockCustomer());
		productRepository.save(TestData.mockProduct());

		mockMvc.perform(post("/order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestData.mockOrderDtoJson()))
				.andDo(print())
				.andExpect(status().isCreated());
	}

	@Test
	void Should_ReturnBadRequest_When_PostOrder_Given_InvalidData() throws Exception {
		customerRepository.save(TestData.mockCustomer());
		productRepository.save(TestData.mockProduct());

		mockMvc.perform(post("/order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestData.mockOrderDtoWithBlankCustomerRegCodeJson()))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}



	@Test
	void Should_ReturnOrders_When_GetOrdersByDate() throws Exception {
		customerRepository.save(TestData.mockCustomer());
		productRepository.save(TestData.mockProduct());
		orderRepository.save(TestData.mockOrder());

		mockMvc.perform(MockMvcRequestBuilders.get("/order/date/" + TestData.DATE_STRING)
						.contentType(MediaType.TEXT_PLAIN))
				.andDo(print())
				.andExpect(status().isFound());
	}

	@Test
	void Should_ReturnBadRequest_When_GetOrdersByDate_Given_InvalidData() throws Exception {
		mockMvc.perform(get("/order/date/" + "123")
						.contentType(MediaType.TEXT_PLAIN))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void Should_ReturnNotFound_When_GetOrder_Given_OrderNotExist() throws Exception {
		mockMvc.perform(get("/order/date/" + TestData.DATE_STRING)
						.contentType(MediaType.TEXT_PLAIN))
				.andDo(print())
				.andExpect(status().isNotFound());
	}



	@Test
	void Should_ReturnOrders_When_GetOrdersByProductSkuCode() throws Exception {
		customerRepository.save(TestData.mockCustomer());
		productRepository.save(TestData.mockProduct());
		orderRepository.save(TestData.mockOrder());

		mockMvc.perform(MockMvcRequestBuilders.get("/order/product/" + TestData.PRODUCT_SKU_CODE)
						.contentType(MediaType.TEXT_PLAIN))
				.andDo(print())
				.andExpect(status().isFound());
	}

	@Test
	void Should_ReturnBadRequest_When_GetOrdersByProduct_Given_InvalidData() throws Exception {
		mockMvc.perform(get("/order/product/"+"   ")
						.contentType(MediaType.TEXT_PLAIN))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void Should_ReturnNotFound_When_GetOrderByProduct_Given_OrderNotExist() throws Exception {
		mockMvc.perform(get("/order/product/" + "AAA")
						.contentType(MediaType.TEXT_PLAIN))
				.andDo(print())
				.andExpect(status().isNotFound());
	}



	@Test
	void Should_ReturnOrders_When_GetOrdersByCustomer() throws Exception {
		customerRepository.save(TestData.mockCustomer());
		productRepository.save(TestData.mockProduct());
		orderRepository.save(TestData.mockOrder());

		mockMvc.perform(MockMvcRequestBuilders.get("/order/customer/" + TestData.CUSTOMER_REG_CODE)
						.contentType(MediaType.TEXT_PLAIN))
				.andDo(print())
				.andExpect(status().isFound());
	}

	@Test
	void Should_ReturnBadRequest_When_GetOrdersByCustomer_Given_InvalidData() throws Exception {
		mockMvc.perform(get("/order/customer/"+ "   ")
						.contentType(MediaType.TEXT_PLAIN))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void Should_ReturnNotFound_When_GetOrdersByCustomer_Given_OrderNotExist() throws Exception {
		mockMvc.perform(get("/order/customer/" + "AAA")
						.contentType(MediaType.TEXT_PLAIN))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}
