package com.ugur.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

    private String id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Integer age;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
