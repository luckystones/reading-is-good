package com.ugur.readingisgood.dto;

import com.ugur.readingisgood.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    @Id
    private String id;

    private String customerId;

    private List<String> bookList;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
