package com.ugur.readingisgood.service;

import com.ugur.readingisgood.dto.MonthlyStatisticsResponseDto;
import com.ugur.readingisgood.dto.StatisticsResponseDto;
import com.ugur.readingisgood.model.Order;
import com.ugur.readingisgood.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ugur.readingisgood.enums.OrderStatus.PURCHASED;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticService {

    private final MongoTemplate mongoTemplate;
    private final OrderRepository orderRepository;

    public AggregationResults getMonthlyReport(String customerId) {

        MatchOperation customerIdAndStatusCriteria = Aggregation.match(new Criteria("customerId").is(customerId));

        ProjectionOperation fields = project("id", "totalSum")
                .and("month(createdAt)").as("month")
                .and("bookList").as("orderBookCount");

        GroupOperation groupBy = group("month").sum("totalSum").as("totalPurchasedAmount")
                .sum("orderBookCount").as("totalBookCount")
                .count().as("totalOrderCount");
        Aggregation aggregation = newAggregation(Order.class, fields, groupBy, customerIdAndStatusCriteria);

        AggregationResults result = mongoTemplate.aggregate(aggregation, "result", Order.class);
        System.out.println(result.getMappedResults());
        log.info(result.getMappedResults().toString());
        return result;
    }

    public MonthlyStatisticsResponseDto getMontlyReportByRepository(String customerId) {
        List<Order> purchasedOrders = orderRepository.findByCustomerIdIsInAndOrderStatus(customerId, PURCHASED);
        Map<Month, List<Order>> monthlyMap = purchasedOrders.stream().collect(Collectors.groupingBy(order -> order.getCreatedAt().getMonth()));
        Map<String, StatisticsResponseDto> collect = monthlyMap.entrySet().stream().map((e) -> {
            Month month = e.getKey();
            List<Order> orders = e.getValue();
            BigDecimal totalOrderAmount = orders.stream().map(Order::getTotalSum).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            Integer totalBookCount = orders.stream().map(Order::getBookList).map(List::size).reduce(Integer::sum).orElse(0);
            Integer totalOrderCount = orders.size();
            return StatisticsResponseDto.builder().totalBookCount(totalBookCount)
                    .month(month.name())
                    .totalOrderCount(totalOrderCount)
                    .totalPurchasedAmount(totalOrderAmount)
                    .build();
        }).collect(Collectors.toMap(StatisticsResponseDto::getMonth, Function.identity()));
        return MonthlyStatisticsResponseDto.builder().monthlyStatistics(collect).build();
    }
}
