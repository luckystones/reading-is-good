package com.ugur.readingisgood.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorDto {

    private ErrorMessageType errorType;

    private String reason;

    private String source;
}
