package com.ticketland.ticketland.ticket.dto;

import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.ticket.domain.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TicketResponse {
    private String ticketId;
    private Integer price;
    private String showTitle;
    private String performer;
    private String seatSection;
    private Integer seatNumber;
    private LocalDateTime showStartTime;
    private Integer duration;

    public static TicketResponse from(Ticket ticket) {
        Show show = ticket.getShow();

        return builder()
                .ticketId(ticket.getId())
                .price(ticket.getPrice())
                .showTitle(show.getTitle())
                .performer(show.getPerformer())
                .seatSection(ticket.getSeatSection())
                .seatNumber(ticket.getSeatNumber())
                .showStartTime(show.getStartTime())
                .duration(show.getDuration())
                .build();
    }
}
