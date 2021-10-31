package com.ugur.readingisgood.mapper;

import com.ugur.readingisgood.dto.OrderRequestDto;
import com.ugur.readingisgood.dto.OrderResponseDto;
import com.ugur.readingisgood.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order mapToModel(OrderRequestDto orderRequestDto);

    OrderResponseDto mapToResponse(Order order);
}
