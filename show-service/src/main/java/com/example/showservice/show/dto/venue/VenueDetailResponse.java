package com.example.showservice.show.dto.venue;

import com.example.showservice.show.domain.Venue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueDetailResponse {
    private Long id;
    private String venueName;
    private String address;
    private String seatLayoutData;
    private Integer seatCount;

    public static VenueDetailResponse from(Venue venue) {
        return builder()
                .id(venue.getId())
                .venueName(venue.getVenueName())
                .address(venue.getAddress())
                .seatLayoutData(venue.getSeatLayoutData())
                .seatCount(venue.getSeatCount())
                .build();
    }
}
