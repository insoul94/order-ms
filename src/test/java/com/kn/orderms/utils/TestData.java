package com.kn.orderms.utils;

import com.kn.orderms.dto.OrderDto;
import com.kn.orderms.dto.OrderLineDto;
import com.kn.orderms.entity.Customer;
import com.kn.orderms.entity.Order;
import com.kn.orderms.entity.OrderLine;
import com.kn.orderms.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static com.kn.orderms.util.HttpUtils.*;

public class TestData {

    public static final String CUSTOMER_REG_CODE = "1234567890";
    public static final String FULL_NAME = "John Wick";
    public static final String EMAIL = "say.hello@gmail.com";
    public static final String TELEPHONE = "+32755544433";
    public static final String PRODUCT_SKU_CODE = "AAA-BBB-CCC-DDD-EEE-111-222-3333";
    public static final String NAME = "Tesla";
    public static final BigDecimal UNIT_PRICE = BigDecimal.valueOf(1000);

    public static final Date DATE = new Date(1667427393000L);
    public static final String DATE_STRING = formatDate(DATE);
    public static final int QUANTITY = 10;


    public static Customer mockCustomer() {
        return Customer.builder()
                .regCode(CUSTOMER_REG_CODE)
                .fullName(FULL_NAME)
                .email(EMAIL)
                .telephone(TELEPHONE)
                .build();
    }


    public static String mockCustomerJson() {
        return toJSON(mockCustomer());
    }


    public static String mockCustomerWithBlankRegCodeJson() {
        Customer customer = mockCustomer();
        customer.setRegCode("");
        return toJSON(customer);
    }


    public static Product mockProduct() {
        return Product.builder()
                .skuCode(PRODUCT_SKU_CODE)
                .name(NAME)
                .unitPrice(UNIT_PRICE)
                .build();
    }


    public static String mockProductJson() {
        return toJSON(mockProduct());
    }


    public static String mockProductWithBlankSkuCodeJson() {
        Product product = mockProduct();
        product.setSkuCode("");
        return toJSON(product);
    }


    public static Order mockOrder() {

        Order order = Order.builder()
                .customer(mockCustomer())
                .date(DATE)
                .build();

        OrderLine orderLine = OrderLine.builder()
                .order(order)
                .product(mockProduct())
                .quantity(QUANTITY)
                .build();

        ArrayList<OrderLine> list = new ArrayList<>();
        list.add(orderLine);
        order.setOrderLines(list);

        return order;
    }


    public static String mockOrderDtoJson() {
        OrderLineDto orderLineDto = OrderLineDto.builder()
                .productSkuCode(PRODUCT_SKU_CODE)
                .quantity(QUANTITY)
                .build();

        ArrayList<OrderLineDto> list = new ArrayList<>();
        list.add(orderLineDto);

        OrderDto orderDto = OrderDto.builder()
                .orderLineDtos(list)
                .dateStr(DATE_STRING)
                .customerRegCode(CUSTOMER_REG_CODE)
                .build();

        return toJSON(orderDto);
    }


    public static String mockOrderDtoWithBlankCustomerRegCodeJson() {
        OrderLineDto orderLineDto = OrderLineDto.builder()
                .productSkuCode(PRODUCT_SKU_CODE)
                .quantity(QUANTITY)
                .build();

        ArrayList<OrderLineDto> list = new ArrayList<>();
        list.add(orderLineDto);

        OrderDto orderDto = OrderDto.builder()
                .orderLineDtos(list)
                .dateStr(DATE_STRING)
                .customerRegCode("")
                .build();

        return toJSON(orderDto);
    }
}
