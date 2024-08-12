package com.ticketland.ticketland.show.dto;

import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.domain.Venue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ShowDetailResponse {
    private ShowSingleResponse show;
    private VenueResponse venue;

    public static ShowDetailResponse from(Show show) {
        Venue findVenue = show.getVenue();

        return builder()
                .show(ShowSingleResponse.from(show))
                .venue(VenueResponse.from(findVenue))
                .build();
    }
}
