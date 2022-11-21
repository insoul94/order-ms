package com.orderms.service;

import com.orderms.dao.CustomerRepository;
import com.orderms.entity.Customer;
import com.orderms.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }


    public Customer readCustomer(String regCode) throws InvalidDataException {
        if (regCode == null || regCode.isBlank()){
            throw new InvalidDataException("regCode must not be blank");
        }

        Optional<Customer> customer = customerRepository.findById(regCode);
        return customer.orElseThrow(() -> new EntityNotFoundException(
                String.format("Customer with reg_code: %s", regCode)));
    }
}
