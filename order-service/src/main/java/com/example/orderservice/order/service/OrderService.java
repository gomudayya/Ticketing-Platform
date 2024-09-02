package com.example.orderservice.order.service;

import com.example.orderservice.client.showservice.ShowServiceClient;
import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import com.example.orderservice.client.showservice.dto.ShowStatus;
import com.example.orderservice.order.domain.Order;
import com.example.orderservice.order.domain.OrderTicket;
import com.example.orderservice.order.dto.OrderDetailsResponse;
import com.example.orderservice.order.dto.OrderPageResponse;
import com.example.orderservice.order.dto.SeatDto;
import com.example.orderservice.order.exception.TicketUnavailableException;
import com.example.orderservice.order.exception.TicketSaleNotActiveException;
import com.example.orderservice.order.repository.OrderRepository;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.service.TicketCacheService;
import com.example.orderservice.ticket.service.TicketService;
import com.example.servicecommon.exception.CustomAccessDeniedException;
import com.example.servicecommon.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final TicketService ticketService;
    private final ShowServiceClient showServiceClient;
    private final TicketCacheService ticketCacheService;

    public OrderDetailsResponse orderTickets(Long userId, Long showId, List<SeatDto> seats) {
        ShowSimpleResponse show = showServiceClient.getShow(showId);
        checkTicketSaleTime(show);

        List<String> ticketCodes = extractTicketCodes(showId, seats);
        List<Ticket> tickets = findTicketsToOrder(showId, ticketCodes);

        tickets.forEach(ticket -> ticket.isSelectedBy(userId));
        List<OrderTicket> orderTickets = tickets.stream().map(OrderTicket::createOrderTicket).toList();

        Order order = Order.createOrder(userId, showId, orderTickets);
        orderRepository.save(order);

        return OrderDetailsResponse.from(order, show);
    }

    private void checkTicketSaleTime(ShowSimpleResponse showSimpleResponse) {
        ShowStatus showStatus = showSimpleResponse.getShowStatus();
        if (showStatus != ShowStatus.TICKET_SALE_ACTIVE) {
            throw new TicketSaleNotActiveException();
        }
    }

    private List<String> extractTicketCodes(Long showId, List<SeatDto> seats) {
        return seats.stream()
                .map(seat -> Ticket.makeCode(showId, seat.getSection(), seat.getNumber()))
                .collect(Collectors.toList());
    }

    private List<Ticket> findTicketsToOrder(Long showId, List<String> ticketCodes) {
        boolean ticketsAvailable;

        try {
            ticketsAvailable = ticketCacheService.isTicketsAvailable(showId, ticketCodes); //레디스에서 모든 티켓이 'AVAILABLE' 하다면 상태를 'SELECTED' 로변경한다
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
            return ticketService.findTicketsWithRock(ticketCodes);  // 레디스 캐시되어있지 않거나, 접근에 문제가 발생했으면 비관적락을 이용해 조회한다.
        }
        
        if (!ticketsAvailable) {
            throw new TicketUnavailableException(); // 모든 티켓이 Available 하지 않다면 예외가 발생한다.
        }

        return ticketService.findTickets(ticketCodes); // 레디스에서 Race-Condition 제어를 마쳤으므로 락 없이 조회한다.
    }

    @Transactional(readOnly = true)
    public OrderDetailsResponse readOrder(Long userId, Long orderId) {
        Order order = findOrder(userId, orderId);
        ShowSimpleResponse show = showServiceClient.getShow(order.getShowId());
        return OrderDetailsResponse.from(order, show);
    }

    @Transactional(readOnly = true)
    public OrderPageResponse findMyOrders(Long userId, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);
        List<Long> showIds = orderPage.getContent().stream().map(Order::getShowId).toList();
        List<ShowSimpleResponse> shows = showServiceClient.getShows(showIds);

        return OrderPageResponse.from(orderPage, shows);
    }

    public OrderDetailsResponse refundOrder(Long userId, Long orderId) {
        Order order = findOrder(userId, orderId);
        ShowSimpleResponse show = showServiceClient.getShow(order.getShowId());
        checkTicketSaleTime(show);

        order.refund();
        return OrderDetailsResponse.from(order, show);
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
