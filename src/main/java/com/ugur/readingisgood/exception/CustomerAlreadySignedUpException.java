package com.ugur.readingisgood.exception;

import java.util.List;

public class CustomerAlreadySignedUpException extends BaseException {
    public CustomerAlreadySignedUpException(String message, List<ErrorDto> errorDtos) {
        super("CustomerAlreadySignedUpException", message, errorDtos);
    }
}
