package com.orderms.dao;

import com.orderms.entity.Customer;
import com.orderms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByDate(Date date);

    List<Order> findByCustomer(Customer customer);
}
