package com.example.orderservice.order.repository.querydsl;

import com.example.orderservice.order.constant.OrderStatus;
import com.example.orderservice.order.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryQuerydsl {
    Page<Order> findByUserIdWithStatusFilter(Long userId, Pageable pageable, OrderStatus... excludedStatus);

}
