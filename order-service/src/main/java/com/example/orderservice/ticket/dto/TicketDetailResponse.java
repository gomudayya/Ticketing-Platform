package com.example.orderservice.ticket.dto;

import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import com.example.orderservice.ticket.domain.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TicketDetailResponse {

    private Long ticketId;
    private Long showId;
    private String showTitle;
    private String performer;
    private LocalDateTime startDate;
    private Integer duration;
    private Integer price;
    private String seatSection;
    private Integer seatNumber;

    public static TicketDetailResponse from(Ticket ticket, ShowSimpleResponse showSimpleResponse) {
        return TicketDetailResponse.builder()
                .ticketId(ticket.getId())
                .showId(ticket.getShowId())
                .showTitle(showSimpleResponse.getTitle())
                .performer(showSimpleResponse.getPerformer())
                .startDate(showSimpleResponse.getStartDate())
                .duration(showSimpleResponse.getDuration())
                .price(ticket.getPrice())
                .seatSection(ticket.getSeatSection())
                .seatNumber(ticket.getSeatNumber())
                .build();
    }
}
