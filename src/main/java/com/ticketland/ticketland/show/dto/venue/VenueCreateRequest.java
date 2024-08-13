package com.ticketland.ticketland.show.dto.venue;

import com.ticketland.ticketland.show.domain.Venue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VenueCreateRequest {
    @NotBlank
    private String venueName;
    @NotBlank
    private String address;
    @NotBlank
    private String seatLayoutData;

    @NotEmpty
    private Map<String, Integer> seatCountDetails; // <Section, 해당 Section 의 좌석갯수>

    public Venue toEntity() {
        return Venue.builder()
                .venueName(venueName)
                .address(address)
                .seatLayoutData(seatLayoutData)
                .build();
    }
}
