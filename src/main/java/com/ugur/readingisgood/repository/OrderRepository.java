package com.ugur.readingisgood.repository;

import com.ugur.readingisgood.enums.OrderStatus;
import com.ugur.readingisgood.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerIdIsIn(String customerId);
    List<Order> findByCustomerIdIsInAndOrderStatus(String customerId, OrderStatus orderStatus);
}
