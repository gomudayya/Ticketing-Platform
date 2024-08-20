package com.example.orderservice.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateRequest {
    private Long showId;

    private List<SeatInfo> seatInfos;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SeatInfo {
        private String section;
        private Integer count;
        private Integer price;
    }
}
