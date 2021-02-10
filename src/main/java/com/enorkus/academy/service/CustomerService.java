package com.enorkus.academy.service;

import com.enorkus.academy.entity.CountryCode;
import com.enorkus.academy.entity.Customer;
import com.enorkus.academy.repository.CustomerRepository;
import com.enorkus.academy.validator.CountryCodeValidator;
import com.enorkus.academy.validator.CustomerAdultValidator;
import com.enorkus.academy.validator.MandatoryValueValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Qualifier("memory-repository")
    private final CustomerRepository customerRepository;

    private final CountryCodeValidator countryCodeValidator;
    private final CustomerAdultValidator customerAdultValidator;
    private final MandatoryValueValidator mandatoryValueValidator;

    public CustomerService(CustomerRepository customerRepository,
                           CountryCodeValidator countryCodeValidator,
                           CustomerAdultValidator customerAdultValidator,
                           MandatoryValueValidator mandatoryValueValidator) {
        this.customerRepository = customerRepository;
        this.countryCodeValidator = countryCodeValidator;
        this.customerAdultValidator = customerAdultValidator;
        this.mandatoryValueValidator = mandatoryValueValidator;
    }


    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer) {
        validateCustomer(customer);

        customer = new Customer.Builder(
                capitalize(customer.getFirstName()),
                capitalize(customer.getLastName()),
                formatNumber(customer.getPersonalNumber())
        )
                .withMiddleName(customer.getMiddleName())
                .withAge(customer.getAge())
                .withCountryCode(
                        customer.getCountryCode().trim().isEmpty() ? CountryCode.NULL :
                        CountryCode.valueOf(customer.getCountryCode().toUpperCase())
                )
                .withCity(customer.getCity())
                .withMonthlyIncome(customer.getMonthlyIncome())
                .withEmployer(customer.getEmployer())
                .withGender(customer.getGender())
                .withMaritalStatus(customer.getMaritalStatus())
                .build();
        customerRepository.insert(customer);
    }

    private void validateCustomer(Customer customer) {
        mandatoryValueValidator.validate(customer.getFirstName(), "First name is mandatory!");
        mandatoryValueValidator.validate(customer.getLastName(), "Last name is mandatory!");
        mandatoryValueValidator.validate(customer.getPersonalNumber(), "Personal number is mandatory!");
        customerAdultValidator.validate(customer.getAge(), "Customer too young!");
        if (!customer.getCountryCode().trim().isEmpty()) {
            countryCodeValidator.validate(customer.getCountryCode().toUpperCase(), "Country code invalid!");
        }
    }

    public void deleteById(String customerId) {
        customerRepository.deleteById(customerId);
    }

    private String capitalize(String s) {
        if (s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private String formatNumber(String s) {
        if (s.length() < 5) {
            return s;
        }
        return new StringBuilder(s).insert(4, "-").toString();
    }
}
