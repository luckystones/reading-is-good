package com.ugur.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    private Integer totalPageNumber;

    @NotNull
    private Double price;

    private Date publishDate;

}
