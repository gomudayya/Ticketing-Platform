package com.example.orderservice.order.repository.querydsl;

import com.example.orderservice.order.constant.OrderStatus;
import com.example.orderservice.order.domain.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.orderservice.order.domain.QOrder.order;

@RequiredArgsConstructor
public class OrderRepositoryQuerydslImpl implements OrderRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Order> findByUserIdWithStatusFilter(Long userId, Pageable pageable, OrderStatus... excludedStatus) {
        List<Order> orders = queryFactory.selectFrom(order)
                .where(order.userId.eq(userId)
                        .and(order.orderStatus.notIn(excludedStatus)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(order.count()).from(order)
                .where(order.userId.eq(userId)
                        .and(order.orderStatus.notIn(OrderStatus.PENDING, OrderStatus.CANCEL)))
                .fetchFirst();

        return new PageImpl<>(orders, pageable, total);
    }
}
