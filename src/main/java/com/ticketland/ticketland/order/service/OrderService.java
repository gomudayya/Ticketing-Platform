package com.ticketland.ticketland.order.service;

import com.ticketland.ticketland.global.exception.CustomAccessDeniedException;
import com.ticketland.ticketland.global.exception.NotFoundException;
import com.ticketland.ticketland.order.constant.OrderStatus;
import com.ticketland.ticketland.order.domain.Order;
import com.ticketland.ticketland.order.dto.OrderPageResponse;
import com.ticketland.ticketland.order.dto.OrderDetailsResponse;
import com.ticketland.ticketland.order.dto.OrderPurchaseRequest;
import com.ticketland.ticketland.order.exception.TicketRefundNotActiveException;
import com.ticketland.ticketland.order.exception.TicketSaleNotActiveException;
import com.ticketland.ticketland.order.repository.OrderRepository;
import com.ticketland.ticketland.show.constant.ShowStatus;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.repository.ShowRepository;
import com.ticketland.ticketland.show.service.ShowService;
import com.ticketland.ticketland.ticket.domain.Ticket;
import com.ticketland.ticketland.show.dto.seat.SeatDto;
import com.ticketland.ticketland.ticket.service.TicketService;
import com.ticketland.ticketland.user.domain.User;
import com.ticketland.ticketland.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShowRepository showRepository;
    private final TicketService ticketService;
    private final UserService userService;

    public OrderDetailsResponse orderTickets(Long userId, OrderPurchaseRequest purchaseDto) {
        User user = userService.findUserById(userId);
        Order order = Order.of(user, OrderStatus.ORDER);
        orderRepository.save(order); // 주문저장

        Long showId = purchaseDto.getShowId();
        List<SeatDto> seatDtos = purchaseDto.getSeats();
        List<Ticket> tickets = seatDtos.stream()
                .map(seatDto -> ticketService.findTicket(showId, seatDto.getSection(), seatDto.getNumber()))
                .toList(); // 주문한 티켓들 조회

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new NotFoundException("공연"));

        if (show.getShowStatus() != ShowStatus.TICKET_SALE_ACTIVE) {
            throw new TicketSaleNotActiveException();
        }

        tickets.forEach(ticket -> ticket.beOrdered(order));
        return OrderDetailsResponse.from(order);
    }

    public OrderDetailsResponse readOrder(Long userId, Long orderId) {
        Order order = findOrder(userId, orderId);
        return OrderDetailsResponse.from(order);
    }

    public OrderPageResponse findMyOrders(Long userId, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);
        return OrderPageResponse.from(orderPage);
    }

    public OrderDetailsResponse refundOrder(Long userId, Long orderId) {
        Order order = findOrder(userId, orderId);
        if (order.getShow().getShowStatus() != ShowStatus.BEFORE_TICKET_OPEN) {
            throw new TicketRefundNotActiveException();
        }

        order.refund();
        return OrderDetailsResponse.from(order);
    }

    public Order findOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("주문"));

        if (!order.isOwnedBy(userId)) {
            throw new CustomAccessDeniedException();
        }

        return order;
    }
}
