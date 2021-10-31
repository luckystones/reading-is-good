package com.ugur.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {

    private String id;

    private String title;

    private String author;

    private Integer totalPageNumber;

    private Double price;

    private Date publishDate;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
