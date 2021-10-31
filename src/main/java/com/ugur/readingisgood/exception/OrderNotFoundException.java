package com.ugur.readingisgood.exception;

import java.util.List;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException(String message, List<ErrorDto> errorDtoList) {
        super("OrderNotFoundException", message, errorDtoList);
    }
}
