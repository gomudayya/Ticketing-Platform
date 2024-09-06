package com.example.orderservice.order.repository;

import com.example.orderservice.order.domain.Order;
import com.example.orderservice.order.repository.querydsl.OrderRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryQuerydsl {
}

