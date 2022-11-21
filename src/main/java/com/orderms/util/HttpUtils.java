package com.orderms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderms.entity.Product;
import com.orderms.dto.OrderDto;
import com.orderms.dto.OrderLineDto;
import com.orderms.entity.Order;
import com.orderms.entity.OrderLine;
import com.orderms.exception.InvalidDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HttpUtils {

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ssZ";

    public static String formatDate(Date date){
        return new SimpleDateFormat(DATE_FORMAT_PATTERN).format(date);
    }

    public static Date parseIntoDate(String str) throws InvalidDataException {
        Date date;
        try {
            date = new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(str);
        } catch (ParseException e) {
            throw new InvalidDataException(
                    String.format("Date string formatting is invalid. Must be: [%s]", HttpUtils.DATE_FORMAT_PATTERN));
        }
        return date;
    }

    public static String toJSON(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static OrderLine parseIntoOrderLine(OrderLineDto orderLineDto, Order order, Product product) {
        return OrderLine.builder()
                .order(order)
                .product(product)
                .quantity(orderLineDto.getQuantity())
                .build();
    }

    public static OrderDto parseIntoOrderDto(Order order) {
        List<OrderLineDto> orderLineDtos = new ArrayList<>();
        for (OrderLine ol : order.getOrderLines()) {
            orderLineDtos.add(parseIntoOrderLineDto(ol));
        }

        return OrderDto.builder()
                .customerRegCode(order.getCustomer().getRegCode())
                .dateStr(formatDate(order.getDate()))
                .orderLineDtos(orderLineDtos)
                .build();
    }

    public static OrderLineDto parseIntoOrderLineDto(OrderLine orderLine) {
        return OrderLineDto.builder()
                .productSkuCode(orderLine.getProduct().getSkuCode())
                .quantity(orderLine.getQuantity())
                .build();
    }
}
