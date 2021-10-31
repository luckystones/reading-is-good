package com.ugur.readingisgood.service;

import com.ugur.readingisgood.dto.OrderRequestDto;
import com.ugur.readingisgood.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    public OrderResponseDto add(OrderRequestDto orderRequestDto) {
        return null;
    }

    public OrderResponseDto remove(String id, OrderRequestDto order) {
        return null;
    }

    public void delete(String id) {

    }
}
