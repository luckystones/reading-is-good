package com.ugur.readingisgood.mapper;

import com.ugur.readingisgood.dto.CustomerResponseDto;
import com.ugur.readingisgood.dto.CustomerSignupRequestDto;
import com.ugur.readingisgood.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer map(CustomerSignupRequestDto customerSignupRequestDto);

    CustomerResponseDto map(Customer customerSignupRequestDto);
}
