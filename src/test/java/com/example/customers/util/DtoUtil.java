package com.example.customers.util;

import com.example.customers.model.CustomerDto;

public class DtoUtil {

    public static CustomerDto getCustomerDto() {
        CustomerDto customerDto = new CustomerDto("John", "Doe", "john.doe@example.com","1234567890");
        customerDto.middleName("M");
        return customerDto;
    }
}
