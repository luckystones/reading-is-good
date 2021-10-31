package com.ugur.readingisgood.model;

import com.ugur.readingisgood.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String id;

    private String customerId;

    private List<String> bookList;

    private OrderStatus orderStatus;

    private BigDecimal totalSum;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
