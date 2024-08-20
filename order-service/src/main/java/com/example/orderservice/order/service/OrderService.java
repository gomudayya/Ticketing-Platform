package com.example.orderservice.order.service;

import com.example.orderservice.client.showservice.ShowServiceClient;
import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import com.example.orderservice.client.showservice.dto.ShowStatus;
import com.example.orderservice.order.domain.Order;
import com.example.orderservice.order.domain.OrderTicket;
import com.example.orderservice.order.dto.OrderDetailsResponse;
import com.example.orderservice.order.dto.OrderPageResponse;
import com.example.orderservice.order.dto.OrderPurchaseRequest;
import com.example.orderservice.order.dto.SeatDto;
import com.example.orderservice.order.exception.TicketRefundNotActiveException;
import com.example.orderservice.order.exception.TicketSaleNotActiveException;
import com.example.orderservice.order.repository.OrderRepository;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.service.TicketService;
import com.example.servicecommon.exception.CustomAccessDeniedException;
import com.example.servicecommon.exception.NotFoundException;
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
    private final ShowServiceClient showServiceClient;
    public OrderDetailsResponse orderTickets(Long userId, OrderPurchaseRequest purchaseDto) {
        ShowSimpleResponse show = showServiceClient.getShow(purchaseDto.getShowId());
        checkTicketSaleTime(show);

        List<Ticket> tickets = findTickets(purchaseDto.getShowId(), purchaseDto.getSeats());
        tickets.forEach(Ticket::bePurchased);

        List<OrderTicket> orderTickets = tickets.stream().map(OrderTicket::createOrderTicket).toList();

        Order order = Order.createOrder(userId, purchaseDto.getShowId(), orderTickets);
        orderRepository.save(order);

        return OrderDetailsResponse.from(order, show);
    }

    private void checkTicketSaleTime(ShowSimpleResponse showSimpleResponse) {
        ShowStatus showStatus = showSimpleResponse.getShowStatus();
        if (showStatus != ShowStatus.TICKET_SALE_ACTIVE) {
            throw new TicketSaleNotActiveException();
        }
    }

    private List<Ticket> findTickets(Long showId, List<SeatDto> seatDtos) {
        return seatDtos.stream()
                .map(seatDto -> ticketService.findTicket(showId, seatDto.getSection(), seatDto.getNumber()))
                .toList(); // 주문할 티켓들 조회
    }

    public OrderDetailsResponse readOrder(Long userId, Long orderId) {
        Order order = findOrder(userId, orderId);
        ShowSimpleResponse show = showServiceClient.getShow(order.getShowId());
        return OrderDetailsResponse.from(order, show);
    }

    public OrderPageResponse findMyOrders(Long userId, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);
        List<Long> showIds = orderPage.getContent().stream().map(Order::getShowId).toList();
        List<ShowSimpleResponse> shows = showServiceClient.getShows(showIds);

        return OrderPageResponse.from(orderPage, shows);
    }

    public OrderDetailsResponse cancelOrder(Long userId, Long orderId) {
        Order order = findOrder(userId, orderId);
        ShowSimpleResponse show = showServiceClient.getShow(order.getShowId());
        if (show.getShowStatus() != ShowStatus.TICKET_SALE_ACTIVE) {
            throw new TicketRefundNotActiveException();
        }

        order.cancel();
        return OrderDetailsResponse.from(order, show);
    }

    private Order findOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("주문"));

        if (!order.isOwnedBy(userId)) {
            throw new CustomAccessDeniedException();
        }

        return order;
    }
}
