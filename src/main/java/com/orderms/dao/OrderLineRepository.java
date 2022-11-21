package com.orderms.dao;

import com.orderms.entity.OrderLine;
import com.orderms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findByProduct(Product product);
}
