package com.example.customers.controller;

import com.example.customers.api.CustomersApi;
import com.example.customers.entity.CustomerEntity;
import com.example.customers.mapper.CustomerMapper;
import com.example.customers.model.CustomerDto;
import com.example.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CustomerController implements CustomersApi {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @Override
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @Override
    public ResponseEntity<CustomerDto> createCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = customerMapper.toEntity(customerDto);
        CustomerEntity customer = customerService.createCustomer(customerEntity);
        return ResponseEntity.ok(customerMapper.toDto(customer));
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomer(UUID id) {
        CustomerEntity customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerMapper.toDto(customer));
    }

    @Override
    public ResponseEntity<CustomerDto> updateCustomer(UUID customerId, CustomerDto customerDto) {
        CustomerEntity updatedCustomer = customerService.updateCustomer(customerId, customerMapper.toEntity(customerDto));
        return ResponseEntity.ok(customerMapper.toDto(updatedCustomer));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(UUID customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

}
