package com.example.customers.mapper;

import com.example.customers.entity.CustomerEntity;
import com.example.customers.model.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    // Convert Entity → Internal DTO
    public CustomerDto toDto(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }

        CustomerDto dto = new CustomerDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        return dto;
    }

    // Convert Internal DTO → Entity
    public CustomerEntity toEntity(CustomerDto dto) {
        if (dto == null) {
            return null;
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        return entity;
    }
}
