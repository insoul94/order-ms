package com.orderms.controller;

import com.orderms.entity.Customer;
import com.orderms.entity.Product;
import com.orderms.exception.InvalidDataException;
import com.orderms.service.CustomerService;
import com.orderms.service.OrderService;
import com.orderms.service.ProductService;
import com.orderms.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class Controller {

    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;

    @Autowired
    public Controller(CustomerService customerService, ProductService productService, OrderService orderService) {
        this.customerService = customerService;
        this.productService = productService;
        this.orderService = orderService;
    }


    @PostMapping(
            path = "/customer",
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid Customer customer) {
        customerService.createCustomer(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(
            path = "/product",
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        productService.createProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(
            path = "/order",
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto) throws InvalidDataException {
        orderService.createOrder(orderDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(
            path = "/order/date/{date}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<List<OrderDto>> getOrdersByDate(@PathVariable String date)
            throws InvalidDataException, EntityNotFoundException {
        return new ResponseEntity<>(orderService.readOrdersByDate(date), HttpStatus.FOUND);
    }

    @GetMapping(
            path = "/order/product/{product_sku_code}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<List<OrderDto>> getOrdersByProductSkuCode(
            @PathVariable(name = "product_sku_code") String productSkuCode)
            throws InvalidDataException, EntityNotFoundException {
        return new ResponseEntity<>(orderService.readOrdersByProductSkuCode(productSkuCode), HttpStatus.FOUND);
    }

    @GetMapping(
            path = "/order/customer/{customer_reg_code}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<List<OrderDto>> getOrdersByCustomer(
            @PathVariable(name = "customer_reg_code") String customerRegCode)
            throws InvalidDataException, EntityNotFoundException {
        return new ResponseEntity<>(orderService.readOrdersByCustomerRegCode(customerRegCode), HttpStatus.FOUND);
    }
}
