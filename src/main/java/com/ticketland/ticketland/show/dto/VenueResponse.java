package com.ticketland.ticketland.show.dto;

import com.ticketland.ticketland.show.domain.Venue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueResponse {
    private Long venueId;
    private String venueName;
    private String address;
    private String seatImage;
    private String seatCount;

    public static VenueResponse from(Venue venue) {
        return builder()
                .venueId(venue.getId())
                .venueName(venue.getVenueName())
                .address(venue.getAddress())
                .seatImage(venue.getSeatImage())
                .seatCount(venue.getSeatCount())
                .build();
    }
}
