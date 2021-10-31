package com.ugur.readingisgood.controller;

import com.ugur.readingisgood.dto.MonthlyStatisticsResponseDto;
import com.ugur.readingisgood.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping(value = "/{customerId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MonthlyStatisticsResponseDto> getMonthlyStatistics(@PathVariable String customerId) {
        return new ResponseEntity<>(statisticService.getMontlyReportByRepository(customerId), HttpStatus.OK);
    }

}
