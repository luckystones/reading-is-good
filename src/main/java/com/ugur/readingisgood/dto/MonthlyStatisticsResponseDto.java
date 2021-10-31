package com.ugur.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyStatisticsResponseDto {

    private Map<String, StatisticsResponseDto> monthlyStatistics;

}
