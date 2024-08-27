package com.example.showservice.venue.dto;

import com.example.showservice.show.dto.SeatCountDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VenueCreateRequest {
    @NotBlank
    private String venueName;
    @NotBlank
    private String address;
    @NotBlank
    private String seatLayoutData;
    @NotEmpty
    private List<SeatCountDto> seatCount; // <Section, 해당 Section 의 좌석갯수>
}
