package com.orderms.service;

import com.orderms.dao.OrderRepository;
import com.orderms.dto.OrderDto;
import com.orderms.dto.OrderLineDto;
import com.orderms.entity.Customer;
import com.orderms.entity.Order;
import com.orderms.entity.OrderLine;
import com.orderms.entity.Product;
import com.orderms.exception.InvalidDataException;
import com.orderms.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderLineService orderLineService;


    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CustomerService customerService,
                        ProductService productService,
                        OrderLineService orderLineService) {
        this.orderRepository = orderRepository;
        this.orderLineService = orderLineService;
        this.customerService = customerService;
        this.productService = productService;
    }


    public void createOrder(OrderDto orderDto) throws InvalidDataException {
        Customer customer = customerService.readCustomer(orderDto.getCustomerRegCode());
        Date date = HttpUtils.parseIntoDate(orderDto.getDateStr());

        Order order = Order.builder()
                .date(date)
                .customer(customer)
                .build();

        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderLineDto oldto : orderDto.getOrderLineDtos()) {
            Product product = productService.readProduct(oldto.getProductSkuCode());
            OrderLine ol = HttpUtils.parseIntoOrderLine(oldto, order, product);
            orderLines.add(ol);
        }

        order.setOrderLines(orderLines);
        orderRepository.save(order);
    }


    public List<OrderDto> readOrdersByDate(String dateStr) throws InvalidDataException, EntityNotFoundException {
        Date date = HttpUtils.parseIntoDate(dateStr);
        List<Order> orders = orderRepository.findByDate(date);

        if (orders.size() == 0) {
            throw new EntityNotFoundException("No orders found by date: " + dateStr);
        }

        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order o : orders) {
            orderDtos.add(HttpUtils.parseIntoOrderDto(o));
        }

        return orderDtos;
    }


    public List<OrderDto> readOrdersByProductSkuCode(String productSkuCode)
            throws InvalidDataException, EntityNotFoundException {

        Product product = productService.readProduct(productSkuCode);
        List<OrderLine> orderLines = orderLineService.readOrderLinesByProduct(product);

        List<Order> orders = new ArrayList<>();
        for (OrderLine ol : orderLines) {
            Order o = orderRepository.findById(ol.getOrder().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                    String.format("Order with ID: %s not found", ol.getOrder().getId())));
            orders.add(o);
        }

        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order o : orders) {
            orderDtos.add(HttpUtils.parseIntoOrderDto(o));
        }

        return orderDtos;
    }

    public List<OrderDto> readOrdersByCustomerRegCode(String customerRegCode) throws InvalidDataException, EntityNotFoundException {

        Customer customer = customerService.readCustomer(customerRegCode);
        List<Order> orders = orderRepository.findByCustomer(customer);

        if (orders.size() == 0) {
            throw new EntityNotFoundException("No orders found by customer_reg_code: " + customerRegCode);
        }

        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order o : orders) {
            orderDtos.add(HttpUtils.parseIntoOrderDto(o));
        }

        return orderDtos;
    }
}
