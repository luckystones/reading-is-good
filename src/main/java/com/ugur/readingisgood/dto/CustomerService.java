package com.ugur.readingisgood.dto;

import com.ugur.readingisgood.exception.CustomerAlreadySignedUpException;
import com.ugur.readingisgood.exception.ErrorDto;
import com.ugur.readingisgood.exception.ErrorMessageType;
import com.ugur.readingisgood.mapper.CustomerMapper;
import com.ugur.readingisgood.model.Customer;
import com.ugur.readingisgood.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerResponseDto signup(CustomerSignupRequestDto customerSignupRequestDto) throws CustomerAlreadySignedUpException {
        validateIfEmailExists(customerSignupRequestDto);
        Customer customer = customerMapper.map(customerSignupRequestDto);
        customerRepository.save(customer);
        return customerMapper.map(customer);
    }

    private void validateIfEmailExists(CustomerSignupRequestDto customerSignupRequestDto) throws CustomerAlreadySignedUpException {
        Customer customer = customerRepository.findByEmail(customerSignupRequestDto.getEmail());
        if (customer != null) {
            ErrorDto errorDto = ErrorDto.builder().reason(String.format("Customer exists with this email: %s! Please try to ", customerSignupRequestDto.getEmail()))
                    .errorType(ErrorMessageType.BUSINESS).build();
            throw new CustomerAlreadySignedUpException(errorDto.getReason(), Arrays.asList(errorDto));
        }
    }

}
