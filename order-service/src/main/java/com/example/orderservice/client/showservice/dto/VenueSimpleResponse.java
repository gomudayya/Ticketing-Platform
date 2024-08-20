package com.example.orderservice.client.showservice.dto;

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
}
