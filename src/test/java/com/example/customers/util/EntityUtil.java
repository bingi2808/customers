package com.example.customers.util;

import com.example.customers.entity.CustomerEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public final class EntityUtil {

    public static CustomerEntity getCustomer() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(UUID.randomUUID());
        customerEntity.setFirstName("John");
        customerEntity.setLastName("Doe");
        customerEntity.setEmail("john.doe@example.com");
        customerEntity.setCreatedAt(LocalDateTime.now());
        customerEntity.setModifiedAt(LocalDateTime.now());
        return customerEntity;
    }
}
