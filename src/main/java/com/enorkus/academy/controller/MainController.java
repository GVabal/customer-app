package com.enorkus.academy.controller;

import com.enorkus.academy.entity.Customer;
import com.enorkus.academy.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    private final CustomerService customerService;

    public MainController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer/all")
    public List<Customer> fetchCustomers() {
        return customerService.getAll();
    }

    @PostMapping("/customer/insert")
    public void insertCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

    @DeleteMapping("/customer/delete/{customerId}")
    public void deleteCustomer(@PathVariable String customerId) {
        customerService.deleteById(customerId);
    }
}
