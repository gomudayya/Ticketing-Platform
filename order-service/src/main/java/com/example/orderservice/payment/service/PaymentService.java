package com.example.orderservice.payment.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final TicketCacheService ticketCacheService;
    public void successPayment(Long userId, PaymentRequest paymentRequest) {
        Long orderId = paymentRequest.getOrderId();

        Order order = orderService.findOrder(userId, orderId);
        if (!Objects.equals(order.getTotalPrice(), paymentRequest.getAmount())) {
            order.cancel();
            throw new InvalidPaymentException();
        }

        Payment payment = Payment.builder()
                .order(order)
                .amount(paymentRequest.getAmount())
                .paymentStatus(PaymentStatus.SUCCESS)
                .build();

        paymentRepository.save(payment);
        order.isPaidBy(userId);
    }

    public void failPayment(Long userId, PaymentRequest paymentRequest) {
        Long orderId = paymentRequest.getOrderId();

        Order order = orderService.findOrder(userId, orderId);
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
