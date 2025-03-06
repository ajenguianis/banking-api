package com.example.bankingapi.config;

import com.example.bankingapi.entity.Customer;
import com.example.bankingapi.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public DataInitializer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        customerRepository.save(new Customer(1L, "Arisha Barron"));
        customerRepository.save(new Customer(2L, "Branden Gibson"));
        customerRepository.save(new Customer(3L, "Rhonda Church"));
        customerRepository.save(new Customer(4L, "Georgina Hazel"));
    }
}