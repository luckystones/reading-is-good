package com.ugur.readingisgood.controller;

import com.ugur.readingisgood.dto.AddToCartRequestDto;
import com.ugur.readingisgood.dto.OrderRequestDto;
import com.ugur.readingisgood.dto.OrderResponseDto;
import com.ugur.readingisgood.dto.RemoveFromCartRequestDto;
import com.ugur.readingisgood.exception.BookOutOfStockException;
import com.ugur.readingisgood.exception.OrderNotFoundException;
import com.ugur.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDto> create(@RequestBody @Validated OrderRequestDto orderRequestDto) throws BookOutOfStockException {
        return new ResponseEntity<>(orderService.create(orderRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{orderId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDto> queryById(@PathVariable String orderId) throws OrderNotFoundException {
        return new ResponseEntity<>(orderService.query(orderId), HttpStatus.OK);
    }

    @PutMapping(value = "/add-to-cart/{orderId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDto> addToCart(@PathVariable @NotEmpty String orderId,
                                                      @RequestBody @Validated AddToCartRequestDto addToCartRequestDto
    ) throws OrderNotFoundException, BookOutOfStockException {
        return new ResponseEntity<>(orderService.addToCart(orderId, addToCartRequestDto), HttpStatus.OK);
    }

    @PutMapping(value = "/remove-from-cart/{orderId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDto> removeFromCart(@PathVariable @NotEmpty String orderId,
                                                           @RequestBody @Validated RemoveFromCartRequestDto removeFromCartRequestDto
    ) throws OrderNotFoundException {
        return new ResponseEntity<>(orderService.removeFromCart(orderId, removeFromCartRequestDto), HttpStatus.OK);
    }

    @PutMapping(value = "/purchase/{orderId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDto> purchase(@PathVariable String orderId) throws OrderNotFoundException {
        return new ResponseEntity<>(orderService.purchase(orderId), HttpStatus.OK);
    }

}
