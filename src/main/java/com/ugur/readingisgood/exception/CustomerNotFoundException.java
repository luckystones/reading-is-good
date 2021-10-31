package com.ugur.readingisgood.exception;

import java.util.List;

public class CustomerNotFoundException extends BaseException {

    public CustomerNotFoundException(String message, List<ErrorDto> errorDtoList) {
        super("CustomerNotFoundException", message, errorDtoList);
    }

}
