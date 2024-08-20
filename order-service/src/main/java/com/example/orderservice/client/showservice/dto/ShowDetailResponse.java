package com.example.orderservice.client.showservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ShowDetailResponse {
    private ShowSimpleResponse show;
    private VenueSimpleResponse venue;
    private List<TicketPriceDto> ticketPrices;
}
