package com.ugur.readingisgood.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Month;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsResponseDto {

    @JsonIgnore
    private String month;

    private Integer totalOrderCount;

    private Integer totalBookCount;

    private BigDecimal totalPurchasedAmount;
}
