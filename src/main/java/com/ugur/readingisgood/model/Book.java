package com.ugur.readingisgood.model;

import com.ugur.readingisgood.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document("book")
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    @Id
    private String id;

    private String title;

    private String author;

    private Integer totalPageNumber;

    private BigDecimal price;

    private Date publishDate;

    private BookStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}