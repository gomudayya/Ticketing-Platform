package com.example.showservice.show.dto.show;

import com.example.showservice.show.domain.Genre;
import com.example.showservice.show.domain.Show;
import com.example.showservice.show.domain.Venue;
import com.example.showservice.show.dto.TicketPriceDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Long venueId;

    @NotNull
    private Long genreId;

    @NotBlank
    private String performer;

    @NotBlank
    private String title;

    @NotBlank
    private String titleImage; // 후.. 원래는 이미지 파일로 올려야 하는데 일단은... 요렇게하자.

    @NotNull
    private LocalDateTime ticketingTime;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private Integer duration;

    @NotEmpty
    private List<TicketPriceDto> ticketPrices;

    public Show toEntity(Genre genre, Venue venue) {
        return Show.builder()
                .genre(genre)
                .venue(venue)
                .title(title)
                .performer(performer)
                .titleImage(titleImage)
                .ticketingTime(ticketingTime)
                .startTime(startDate)
                .duration(duration)
                .build();
    }
}


