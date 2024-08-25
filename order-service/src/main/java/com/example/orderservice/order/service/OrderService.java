package com.example.orderservice.order.service;

import com.example.orderservice.client.showservice.ShowServiceClient;
import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import com.example.orderservice.client.showservice.dto.ShowStatus;
import com.example.orderservice.order.domain.Order;
import com.example.orderservice.order.domain.OrderTicket;
import com.example.orderservice.order.dto.OrderDetailsResponse;
import com.example.orderservice.order.dto.OrderPageResponse;
import com.example.orderservice.order.dto.SeatDto;
import com.example.orderservice.order.exception.TicketRefundNotActiveException;
import com.example.orderservice.order.exception.TicketSaleNotActiveException;
import com.example.orderservice.order.repository.OrderRepository;
import com.example.orderservice.ticket.domain.CachedTicket;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.service.TicketCacheService;
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
    private final TicketCacheService ticketCacheService;

    /**
     * 사용자가 좌석을 고르고, 결제하기 버튼을 누르면 티켓이 selected 상태가 된다.
     * selected 상태일 때 다른 사용자가 주문할 수 없다.
     *
     * 이후에 사용자가 성공적으로 결제를 완료하면 티켓은 sold 상태가 된다.
     * 사용자가 결제를 실패하면 티켓은 available 상태가 된다.
     */
    public OrderDetailsResponse orderTickets(Long userId, Long showId, List<SeatDto> seats) {
        ShowSimpleResponse show = showServiceClient.getShow(showId);
        checkTicketSaleTime(show);

//        // 1. 먼저 캐시저장소를 찔러 본다.
//        List<CachedTicket> cachedTickets = ticketCacheService.findCachedTickets(showId, seats);
//        if (alreadyReserved(cachedTickets)) {
//            throw new TicketAlreadyReservedException();
//        }
//
//        if (cachedTickets.size() == seats.size()) { // 주문할 티켓들이 모두 캐시되어있을 경우 (캐싱이 아직 만료되지 않은 경우)
////            cachedTickets.forEach();
//        }

        List<Ticket> tickets = ticketService.findTicketsWithRock(showId, seats);
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

    private boolean alreadyReserved(List<CachedTicket> cachedTickets) {
        return cachedTickets.stream()
                .anyMatch(CachedTicket::isReserved);
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
        if (show.getShowStatus() != ShowStatus.TICKET_SALE_ACTIVE) {
            throw new TicketRefundNotActiveException();
        }

        order.cancel();
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
