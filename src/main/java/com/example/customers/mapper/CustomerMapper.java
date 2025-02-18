package com.example.customers.mapper;

import com.example.customers.model.CustomerDto;
import com.example.customers.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    // Convert JPA Entity → Customer DTO
    CustomerDto toDto(CustomerEntity entity);

    // Convert Customer DTO → JPA Entity
    CustomerEntity toEntity(CustomerDto dto);
}
