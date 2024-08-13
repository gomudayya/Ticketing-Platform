package com.ticketland.ticketland.show.dto.show;

import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.domain.Venue;
import com.ticketland.ticketland.show.dto.ticket.TicketPriceDto;
import com.ticketland.ticketland.show.dto.venue.VenueSimpleResponse;
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
    private ShowSingleResponse show;
    private VenueSimpleResponse venue;
    private List<TicketPriceDto> ticketPrices;
    public static ShowDetailResponse from(Show show) {
        Venue findVenue = show.getVenue();
        List<TicketPriceDto> ticketPriceDtos = show.getTicketPrices().stream()
                .map(TicketPriceDto::from)
                .toList();

        return builder()
                .show(ShowSingleResponse.from(show))
                .venue(VenueSimpleResponse.from(findVenue))
                .ticketPrices(ticketPriceDtos)
                .build();
    }
}
