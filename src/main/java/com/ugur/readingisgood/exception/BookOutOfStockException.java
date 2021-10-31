package com.ugur.readingisgood.exception;

import java.util.List;

public class BookOutOfStockException extends BaseException {
    public BookOutOfStockException(String message, List<ErrorDto> errorDtos) {
        super("BookOutOfStockException", message, errorDtos);
    }
}
