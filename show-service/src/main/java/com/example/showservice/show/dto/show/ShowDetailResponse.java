package com.example.showservice.show.dto.show;

import com.example.showservice.show.domain.Show;
import com.example.showservice.show.domain.Venue;
import com.example.showservice.show.dto.TicketPriceDto;
import com.example.showservice.show.dto.venue.VenueSimpleResponse;
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
    public static ShowDetailResponse from(Show show) {
        Venue findVenue = show.getVenue();
        List<TicketPriceDto> ticketPriceDtos = show.getTicketPrices().stream()
                .map(TicketPriceDto::from)
                .toList();

        return builder()
                .show(ShowSimpleResponse.from(show))
                .venue(VenueSimpleResponse.from(findVenue))
                .ticketPrices(ticketPriceDtos)
                .build();
    }
}
