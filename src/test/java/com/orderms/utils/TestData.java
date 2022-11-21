package com.orderms.utils;

import com.orderms.dto.OrderDto;
import com.orderms.dto.OrderLineDto;
import com.orderms.entity.Customer;
import com.orderms.entity.Order;
import com.orderms.entity.OrderLine;
import com.orderms.entity.Product;
import com.orderms.util.HttpUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class TestData {

    public static final String CUSTOMER_REG_CODE = "1234567890";
    public static final String FULL_NAME = "John Wick";
    public static final String EMAIL = "say.hello@gmail.com";
    public static final String TELEPHONE = "+32755544433";
    public static final String PRODUCT_SKU_CODE = "AAA-BBB-CCC-DDD-EEE-111-222-3333";
    public static final String NAME = "Tesla";
    public static final BigDecimal UNIT_PRICE = BigDecimal.valueOf(1000);

    public static final Date DATE = new Date(1667427393000L);
    public static final String DATE_STRING = HttpUtils.formatDate(DATE);
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
        return HttpUtils.toJSON(mockCustomer());
    }


    public static String mockCustomerWithBlankRegCodeJson() {
        Customer customer = mockCustomer();
        customer.setRegCode("");
        return HttpUtils.toJSON(customer);
    }


    public static Product mockProduct() {
        return Product.builder()
                .skuCode(PRODUCT_SKU_CODE)
                .name(NAME)
                .unitPrice(UNIT_PRICE)
                .build();
    }


    public static String mockProductJson() {
        return HttpUtils.toJSON(mockProduct());
    }


    public static String mockProductWithBlankSkuCodeJson() {
        Product product = mockProduct();
        product.setSkuCode("");
        return HttpUtils.toJSON(product);
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

        return HttpUtils.toJSON(orderDto);
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

        return HttpUtils.toJSON(orderDto);
    }
}
