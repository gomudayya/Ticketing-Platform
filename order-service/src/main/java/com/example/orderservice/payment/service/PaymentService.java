package com.example.orderservice.payment.service;

import com.example.orderservice.order.constant.OrderStatus;
import com.example.orderservice.order.domain.Order;
import com.example.orderservice.order.service.OrderService;
import com.example.orderservice.payment.constant.PaymentStatus;
import com.example.orderservice.payment.domain.Payment;
import com.example.orderservice.payment.dto.PaymentRequest;
import com.example.orderservice.payment.exception.InvalidPaymentException;
import com.example.orderservice.payment.repository.PaymentRepository;
import com.example.orderservice.ticket.constant.TicketStatus;
import com.example.orderservice.ticket.service.TicketCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final TicketCacheService ticketCacheService;
    public void successPayment(Long userId, PaymentRequest paymentRequest) {
        Order order = orderService.findOrder(userId, paymentRequest.getOrderId());
        checkPaymentAmount(userId, paymentRequest, order);

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            log.warn("PENDING 상태가 아닌 주문에 대한 결제 발생. 사용자 ID: {}, 주문 ID: {}, 결제 정보: {}", userId, order.getId(), paymentRequest);
            throw new InvalidPaymentException();
        }

        order.successBy(userId);
        Payment payment = Payment.builder()
                .order(order)
                .amount(paymentRequest.getAmount())
                .paymentStatus(PaymentStatus.SUCCESS)
                .build();

        paymentRepository.save(payment);
    }

    private void checkPaymentAmount(Long userId, PaymentRequest paymentRequest, Order order) {
        if (!Objects.equals(order.getTotalPrice(), paymentRequest.getAmount())) {
            log.error("결제 금액과 주문서의 금액이 일치하지 않습니다. - 사용자 ID: {}, 주문 ID: {}, 결제 요청 : {}", userId, order.getId(), paymentRequest);
            order.cancel();
            throw new InvalidPaymentException();
        }
    }

    public void failPayment(Long userId, PaymentRequest paymentRequest) {
        Order order = orderService.findOrder(userId, paymentRequest.getOrderId());

        if (order.getOrderStatus() == OrderStatus.SUCCESS) {
            log.error("이미 완료된 주문에 대한 결제 실패 발생. - 사용자 ID: {}, 주문 ID: {}, 결제 요청: {}", userId, paymentRequest.getOrderId(), paymentRequest);
            throw new InvalidPaymentException();
        }

        order.cancel();
        Payment payment = Payment.builder()
                .order(order)
                .amount(paymentRequest.getAmount())
                .paymentStatus(PaymentStatus.FAIL)
                .build();

        ticketCacheService.changeTicketStatus(order.getTickets(), TicketStatus.AVAILABLE);
        paymentRepository.save(payment);
    }
}
