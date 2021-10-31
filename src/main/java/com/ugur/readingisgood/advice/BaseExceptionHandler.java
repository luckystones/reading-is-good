package com.ugur.readingisgood.advice;

import com.ugur.readingisgood.exception.BaseException;
import com.ugur.readingisgood.exception.BookOutOfStockException;
import com.ugur.readingisgood.exception.CustomerAlreadySignedUpException;
import com.ugur.readingisgood.exception.CustomerNotFoundException;
import com.ugur.readingisgood.exception.ErrorDto;
import com.ugur.readingisgood.exception.ErrorMessageType;
import com.ugur.readingisgood.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ugur.readingisgood.exception.ErrorMessageType.RUNTIME_EXCEPTION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;


@Slf4j
@ControllerAdvice(basePackages = "com.ugur")
public class BaseExceptionHandler {

    @ExceptionHandler(value = {BookOutOfStockException.class})
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ResponseEntity<BaseException> handleOutOfStockException(BookOutOfStockException ex) {
        log.error("Books are sold out {}", ex.getMessage());
        return new ResponseEntity<>(ex, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ResponseEntity<BaseException> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        log.error("Customer is not registered yet {}", ex.getMessage());
        return new ResponseEntity<>(ex, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {CustomerAlreadySignedUpException.class})
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<BaseException> handleCustomerNotFoundException(CustomerAlreadySignedUpException ex) {
        log.error("Customer is not registered yet {}", ex.getMessage());
        return new ResponseEntity<>(ex, BAD_REQUEST);
    }

    @ExceptionHandler(value = {OrderNotFoundException.class})
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ResponseEntity<BaseException> handleOrderNotFoundException(OrderNotFoundException ex) {
        log.error("Order not found {}", ex.getMessage());
        return new ResponseEntity<>(ex, UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {HttpClientErrorException.class, AuthenticationException.class})
    public ResponseEntity<BaseException> handleUnauthorizedException(HttpClientErrorException.Unauthorized ex) {
        log.error("Unauthorized Exception: {}", ex.getMessage());
        ErrorDto errorDto = ErrorDto.builder().errorType(ErrorMessageType.AUTHENTICATION).reason(ex.getMessage()).source("Authentication")
                .reason("Basic authentication should be provided")
                .build();
        BaseException baseException = new BaseException(HttpClientErrorException.Unauthorized.class.getName(), ex.getMessage(), ex, Arrays.asList(errorDto));
        return new ResponseEntity<>(baseException, HttpStatus.UNAUTHORIZED);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<BaseException> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Unauthorized Exception: {}", ex.getMessage());
        BindingResult result = ex.getBindingResult();
        List<ObjectError> errors = result.getAllErrors();
        List<ErrorDto> errorDtos = errors.stream().map(this::getErrorDto).collect(Collectors.toList());
        BaseException baseException = new BaseException(ex.getClass().getName(), ex.getMessage(), ex, errorDtos);
        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    private ErrorDto getErrorDto(ObjectError error) {
        String objectName = error.getObjectName();
        return ErrorDto.builder().source(String.format("objectName: %s field: %s", objectName, ((FieldError) error).getField()))
                .reason("Invalid input parameter")
                .errorType(ErrorMessageType.VALIDATION).build();
    }

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<BaseException> handleException(Exception ex) {
        ErrorDto errorDto = ErrorDto.builder().errorType(RUNTIME_EXCEPTION).reason(ex.getMessage()).source("Unknown error occured")
                .build();
        BaseException baseException = new BaseException(ex.getClass().getName(), ex.getMessage(), ex, Arrays.asList(errorDto));
        return new ResponseEntity<>(baseException, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
