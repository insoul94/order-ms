package com.orderms.service;

import com.orderms.dao.OrderLineRepository;
import com.orderms.entity.OrderLine;
import com.orderms.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    @Autowired
    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public List<OrderLine> readOrderLinesByProduct(Product product) {
        List<OrderLine> orderLines = orderLineRepository.findByProduct(product);
        if (orderLines.size() == 0) {
            throw new EntityNotFoundException(
                    String.format("No orders lines found for Product name: %s, SKU code: %s",
                            product.getName(), product.getSkuCode()));
        }
        return orderLines;
    }
}
