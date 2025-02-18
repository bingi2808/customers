package com.example.customers.service;

import com.example.customers.entity.CustomerEntity;
import com.example.customers.exception.ResourceNotFoundException;
import com.example.customers.repository.CustomerRepository;
import com.example.customers.util.EntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerEntity customer;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        customer = EntityUtil.getCustomer();
    }

    @Test
    void testCreateCustomer_Success() {
        // Given
        when(customerRepository.save(customer)).thenReturn(customer);

        // When
        CustomerEntity savedCustomer = customerService.createCustomer(customer);

        // Then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getEmail()).isEqualTo("john.doe@example.com");
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testGetCustomerById_CustomerExists() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // When
        CustomerEntity foundCustomer = customerService.getCustomerById(customerId);

        // Then
        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getId()).isEqualTo(customerId);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testGetCustomerById_CustomerNotFound() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(customerId));
        verify(customerRepository, times(1)).findById(customerId);
    }


    @Test
    void testGetAllCustomers() {
        // Given
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        // When
        List<CustomerEntity> customers = customerService.getAllCustomers();

        // Then
        assertThat(customers).hasSize(1);
        verify(customerRepository, times(1)).findAll();
    }
}
