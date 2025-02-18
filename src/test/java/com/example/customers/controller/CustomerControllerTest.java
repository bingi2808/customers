package com.example.customers.controller;

import com.example.customers.entity.CustomerEntity;
import com.example.customers.model.CustomerDto;
import com.example.customers.mapper.CustomerMapper;
import com.example.customers.service.CustomerService;
import com.example.customers.util.DtoUtil;
import com.example.customers.util.EntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerController customerController;

    private CustomerEntity customerEntity;
    private CustomerDto customerDto;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        customerEntity = EntityUtil.getCustomer();
        customerId = customerEntity.getId();
        customerDto = DtoUtil.getCustomerDto();
    }

    @Test
    void testGetAllCustomers() {
        // Given
        when(customerService.getAllCustomers()).thenReturn(singletonList(customerEntity));
        when(customerMapper.toDto(customerEntity)).thenReturn(customerDto);

        // When
        ResponseEntity<List<CustomerDto>> response = customerController.getAllCustomers();

        // Then
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getEmail()).isEqualTo("john.doe@example.com");
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testCreateCustomer() {
        // Given
        when(customerMapper.toEntity(customerDto)).thenReturn(customerEntity);
        when(customerService.createCustomer(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.toDto(customerEntity)).thenReturn(customerDto);

        // When
        ResponseEntity<CustomerDto> response = customerController.createCustomer(customerDto);

        // Then
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("john.doe@example.com");
        verify(customerService, times(1)).createCustomer(customerEntity);
    }

    @Test
    void testGetCustomerById() {
        // Given
        when(customerService.getCustomerById(customerId)).thenReturn(customerEntity);
        when(customerMapper.toDto(customerEntity)).thenReturn(customerDto);

        // When
        ResponseEntity<CustomerDto> response = customerController.getCustomer(customerId);

        // Then
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("john.doe@example.com");
        verify(customerService, times(1)).getCustomerById(customerId);
    }
}
