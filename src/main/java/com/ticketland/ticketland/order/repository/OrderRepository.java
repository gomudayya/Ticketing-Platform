package com.ticketland.ticketland.order.repository;

import com.ticketland.ticketland.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
