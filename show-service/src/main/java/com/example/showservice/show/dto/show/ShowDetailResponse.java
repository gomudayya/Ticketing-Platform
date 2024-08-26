package com.example.showservice.show.dto.show;

import com.example.showservice.show.domain.Show;
import com.example.showservice.venue.domain.Venue;
import com.example.showservice.show.dto.ShowSeatDto;
import com.example.showservice.venue.dto.VenueSimpleResponse;
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
    private List<ShowSeatDto> showSeatInfos;
    public static ShowDetailResponse from(Show show) {
        Venue findVenue = show.getVenue();
        List<ShowSeatDto> showSeatDtoList = show.getShowSeats().stream()
                .map(ShowSeatDto::from)
                .toList();

        return builder()
                .show(ShowSimpleResponse.from(show))
                .venue(VenueSimpleResponse.from(findVenue))
                .showSeatInfos(showSeatDtoList)
                .build();
    }
}
