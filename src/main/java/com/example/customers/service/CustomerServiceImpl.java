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
    public CustomerEntity updateCustomer(UUID id, CustomerEntity updatedCustomer) {
        CustomerEntity existingCustomer = getCustomerById(id);

        // Update only necessary fields
        existingCustomer.setFirstName(updatedCustomer.getFirstName());
        existingCustomer.setMiddleName(updatedCustomer.getMiddleName());
        existingCustomer.setLastName(updatedCustomer.getLastName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found");
        }
        customerRepository.deleteById(id);
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
