package com.example.customers.service;

import com.example.customers.entity.CustomerEntity;

import java.util.List;
import java.util.UUID;

public class CustomerServiceImpl implements CustomerService{
    @Override
    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        return null;
    }

    @Override
    public CustomerEntity getCustomerById(UUID id) {
        return null;
    }

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return List.of();
    }
}
