package com.ticketland.ticketland.show.dto.show;

import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.domain.Venue;
import com.ticketland.ticketland.show.dto.venue.VenueSimpleResponse;
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
    private VenueSimpleResponse venue;

    public static ShowDetailResponse from(Show show) {
        Venue findVenue = show.getVenue();

        return builder()
                .show(ShowSingleResponse.from(show))
                .venue(VenueSimpleResponse.from(findVenue))
                .build();
    }
}
