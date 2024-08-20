package com.example.showservice.show.dto.venue;

import com.example.showservice.show.domain.Venue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueSimpleResponse {
    private Long venueId;
    private String venueName;
    private String address;
    private Integer seatCount;

    public static VenueSimpleResponse from(Venue venue) {
        return builder()
                .venueId(venue.getId())
                .venueName(venue.getVenueName())
                .address(venue.getAddress())
                .seatCount(venue.getSeatCount())
                .build();
    }
}
