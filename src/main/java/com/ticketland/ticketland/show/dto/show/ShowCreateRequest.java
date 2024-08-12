package com.ticketland.ticketland.show.dto.show;

import com.ticketland.ticketland.show.domain.Genre;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.domain.Venue;
import com.ticketland.ticketland.show.dto.TicketPriceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowCreateRequest {
    private Long venueId;
    private Long genreId;
    private String performer;
    private String title;
    private String descriptionImage; // 후.. 원래는 이미지 파일로 올려야 하는데 일단은... 요렇게하자.
    private LocalDateTime ticketingTime;
    private LocalDateTime startDate;
    private Integer duration;
    private List<TicketPriceDto> ticketPrices;

    public Show toEntity(Genre genre, Venue venue) {
        return Show.builder()
                .genre(genre)
                .venue(venue)
                .title(title)
                .performer(performer)
                .descriptionImage(descriptionImage)
                .ticketingTime(ticketingTime)
                .startTime(startDate)
                .duration(duration)
                .build();
    }
}


