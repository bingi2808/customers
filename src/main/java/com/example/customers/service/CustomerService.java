package com.example.customers.service;

import com.example.customers.entity.CustomerEntity;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerEntity createCustomer(CustomerEntity customerEntity);
    CustomerEntity getCustomerById(UUID id);
    List<CustomerEntity> getAllCustomers();
}
