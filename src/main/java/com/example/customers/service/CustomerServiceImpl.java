package com.example.customers.service;

import com.example.customers.entity.CustomerEntity;
import com.example.customers.exception.ResourceNotFoundException;
import com.example.customers.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        if (customerRepository.findByEmail(customerEntity.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        return customerRepository.save(customerEntity);
    }

    @Override
    public CustomerEntity getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
}
