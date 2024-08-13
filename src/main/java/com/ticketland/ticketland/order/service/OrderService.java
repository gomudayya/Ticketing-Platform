package com.ticketland.ticketland.order.service;

import com.ticketland.ticketland.global.exception.CustomAccessDeniedException;
import com.ticketland.ticketland.global.exception.NotFoundException;
import com.ticketland.ticketland.order.constant.OrderStatus;
import com.ticketland.ticketland.order.domain.Order;
import com.ticketland.ticketland.order.dto.OrderPageResponse;
import com.ticketland.ticketland.order.dto.OrderDetailsResponse;
import com.ticketland.ticketland.order.dto.OrderPurchaseRequest;
import com.ticketland.ticketland.order.repository.OrderRepository;
import com.ticketland.ticketland.show.domain.Ticket;
import com.ticketland.ticketland.show.dto.seat.SeatDto;
import com.ticketland.ticketland.show.service.TicketService;
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

        tickets.forEach(ticket -> ticket.beOrdered(order));
        return OrderDetailsResponse.from(order);
    }

    public OrderDetailsResponse findOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("주문"));

        if (!order.isOwnedBy(userId)) {
            throw new CustomAccessDeniedException();
        }

        return OrderDetailsResponse.from(order);
    }

    public OrderPageResponse findMyOrders(Long userId, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);
        return OrderPageResponse.from(orderPage);
    }
}
