package com.ugur.readingisgood.service;

import com.google.common.collect.Collections2;
import com.ugur.readingisgood.dto.AddToCartRequestDto;
import com.ugur.readingisgood.dto.CustomerOrderQueryResponseDto;
import com.ugur.readingisgood.dto.OrderRequestDto;
import com.ugur.readingisgood.dto.OrderResponseDto;
import com.ugur.readingisgood.dto.RemoveFromCartRequestDto;
import com.ugur.readingisgood.enums.BookStatus;
import com.ugur.readingisgood.enums.OrderStatus;
import com.ugur.readingisgood.exception.BookOutOfStockException;
import com.ugur.readingisgood.exception.ErrorDto;
import com.ugur.readingisgood.exception.ErrorMessageType;
import com.ugur.readingisgood.exception.OrderNotFoundException;
import com.ugur.readingisgood.mapper.OrderMapper;
import com.ugur.readingisgood.model.Book;
import com.ugur.readingisgood.model.Order;
import com.ugur.readingisgood.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.ugur.readingisgood.enums.BookStatus.AVAILABLE;
import static com.ugur.readingisgood.enums.BookStatus.IN_CART;
import static com.ugur.readingisgood.enums.OrderStatus.INITIAL;
import static com.ugur.readingisgood.enums.OrderStatus.PURCHASED;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final BookService bookService;

    @Transactional
    public OrderResponseDto create(OrderRequestDto orderRequestDto) throws BookOutOfStockException {
        List<Book> allOrderBooks = bookService.getAllBooksById(orderRequestDto.getBookList());
        validateIfBooksStillAvailable(allOrderBooks);
        Order order = orderMapper.mapToModel(orderRequestDto);
        order.setTotalSum(calculateTotalOrderAmount(allOrderBooks));
        bookService.setBooksStatus(allOrderBooks, IN_CART);
        order.setOrderStatus(INITIAL);
        return orderMapper.mapToResponse(orderRepository.save(order));
    }

    private BigDecimal calculateTotalOrderAmount(List<Book> allOrderBooks) {
        return allOrderBooks.stream().map(Book::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private void validateIfBooksStillAvailable(List<Book> orderBooks) throws BookOutOfStockException {
        List<Book> outOfStockBooks = orderBooks.stream().filter(b -> !BookStatus.AVAILABLE.equals(b.getStatus())).collect(toList());
        if (!CollectionUtils.isEmpty(outOfStockBooks)) {
            String soldOutBooks = outOfStockBooks.stream()
                    .map(b -> format("Id: %s Title: %s,%n", b.getId(), b.getTitle()))
                    .collect(joining(""));
            ErrorDto errorDto = ErrorDto.builder().reason(format("Books with following ids are out of stock now: %s%n", soldOutBooks))
                    .errorType(ErrorMessageType.BUSINESS)
                    .source("Book")
                    .build();
            throw new BookOutOfStockException(errorDto.getReason(), Arrays.asList(errorDto));
        }
    }

    @Transactional
    public OrderResponseDto addToCart(String orderId, AddToCartRequestDto addToCartRequestDto) throws OrderNotFoundException, BookOutOfStockException {
        Order order = orderRepository.findById(orderId).orElseThrow(notFoundExceptionSupplier(orderId));
        order.getBookList().addAll(addToCartRequestDto.getBookList());
        List<Book> allOrderBooks = bookService.getAllBooksById(addToCartRequestDto.getBookList());
        validateIfBooksStillAvailable(allOrderBooks);
        bookService.setBooksStatus(allOrderBooks, IN_CART);
        return orderMapper.mapToResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponseDto removeFromCart(String orderId, RemoveFromCartRequestDto removeFromCartRequestDto) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(notFoundExceptionSupplier(orderId));
        List<Book> allOrderBooks = bookService.getAllBooksById(removeFromCartRequestDto.getBookList());
        bookService.setBooksStatus(allOrderBooks, AVAILABLE);
        order.getBookList().removeAll(removeFromCartRequestDto.getBookList());
        return orderMapper.mapToResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponseDto purchase(String orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(notFoundExceptionSupplier(orderId));
        List<Book> allOrderBooks = bookService.getAllBooksById(order.getBookList());
        bookService.setBooksStatus(allOrderBooks, BookStatus.SOLD);
        order.setOrderStatus(PURCHASED);
        return orderMapper.mapToResponse(orderRepository.save(order));
    }


    private Supplier<OrderNotFoundException> notFoundExceptionSupplier(String orderId) {
        ErrorDto errorDto = ErrorDto.builder().errorType(ErrorMessageType.VALIDATION)
                .reason(format("OrderId: %s not found", orderId))
                .source("order").build();
        return () -> new OrderNotFoundException(errorDto.getReason(), Arrays.asList(errorDto));
    }

    public CustomerOrderQueryResponseDto getOrdersByCustomerId(String customerId) {
        List<OrderResponseDto> customerOrders = orderRepository.findByCustomerIdIsIn(customerId)
                .stream().map(orderMapper::mapToResponse)
                .collect(toList());
        return CustomerOrderQueryResponseDto.builder().orderResponseDtos(customerOrders).build();
    }

    public OrderResponseDto query(String orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId)
                .map(orderMapper::mapToResponse)
                .orElseThrow(notFoundExceptionSupplier(orderId));
    }
}
