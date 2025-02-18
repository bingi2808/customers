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
        customer = EntityUtil.getCustomer();
        customerId = customer.getId();
    }

    @Test
    void testCreateCustomer_Success() {
        // Given
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.save(customer)).thenReturn(customer);

        // When
        CustomerEntity savedCustomer = customerService.createCustomer(customer);

        // Then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getEmail()).isEqualTo("john.doe@example.com");
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testCreateCustomer_EmailAlreadyExists() {
        // Given
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(customer));
        verify(customerRepository, times(1)).findByEmail(customer.getEmail());
        verify(customerRepository, never()).save(any());
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

    @Test
    void testUpdateCustomer_CustomerExists() {
        // Given
        CustomerEntity updatedCustomer = EntityUtil.getCustomer();
        updatedCustomer.setFirstName("Jane");
        updatedCustomer.setLastName("Smith");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(CustomerEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        CustomerEntity result = customerService.updateCustomer(customerId, updatedCustomer);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Jane");
        assertThat(result.getLastName()).isEqualTo("Smith");
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void testUpdateCustomer_CustomerNotFound() {
        // Given
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(customerId, customer));
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testDeleteCustomer_CustomerExists() {
        // Given
        when(customerRepository.existsById(customerId)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(customerId);

        // When
        customerService.deleteCustomer(customerId);

        // Then
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void testDeleteCustomer_CustomerNotFound() {
        // Given
        when(customerRepository.existsById(customerId)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(customerId));
        verify(customerRepository, times(1)).existsById(customerId);
        verify(customerRepository, never()).deleteById(any());
    }
}
