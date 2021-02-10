package com.enorkus.academy.repository;

import com.enorkus.academy.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository {

    List<Customer> findAll();

    void insert(Customer customer);

    void deleteById(String customerId);
}
