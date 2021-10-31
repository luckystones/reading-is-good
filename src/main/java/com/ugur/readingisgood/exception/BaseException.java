package com.ugur.readingisgood.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"cause", "localizedMessage", "stackTrace", "suppressed", "@type"})
@JsonPropertyOrder({"name", "errors", "message", "urlStack", "name", "description"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseException extends Exception {

    protected String name;

    protected List<ErrorDto> errors;

    public BaseException(String name, String message, Throwable throwable, List<ErrorDto> errorDtos) {
        super(message, throwable);
        this.name = name;
        this.errors = errorDtos;
    }

    public BaseException(String name, String message, List<ErrorDto> errorDtos) {
        super(message);
        this.name = name;
        this.errors = errorDtos;
    }
}
