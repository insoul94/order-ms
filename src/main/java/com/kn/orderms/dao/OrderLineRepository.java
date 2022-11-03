package com.kn.orderms.dao;

import com.kn.orderms.entity.OrderLine;
import com.kn.orderms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findByProduct(Product product);
}
