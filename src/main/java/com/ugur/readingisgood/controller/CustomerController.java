package com.ugur.readingisgood.controller;

import com.ugur.readingisgood.dto.CustomerOrderQueryResponseDto;
import com.ugur.readingisgood.dto.CustomerResponseDto;
import com.ugur.readingisgood.dto.CustomerService;
import com.ugur.readingisgood.dto.CustomerSignupRequestDto;
import com.ugur.readingisgood.exception.CustomerAlreadySignedUpException;
import com.ugur.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "customers")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    @PostMapping(value = "/signup", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDto> signup(@Validated @RequestBody CustomerSignupRequestDto customerSignupRequestDto
    ) throws CustomerAlreadySignedUpException {
        return new ResponseEntity<>(customerService.signup(customerSignupRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/query-orders/{customerId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerOrderQueryResponseDto> queryCustomerOrders(@PathVariable @NotEmpty String customerId) {
        return new ResponseEntity<>(orderService.getOrdersByCustomerId(customerId), HttpStatus.OK);
    }
}
