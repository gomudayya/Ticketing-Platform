package com.example.showservice.show.dto.show;

import com.example.showservice.show.domain.Genre;
import com.example.showservice.show.domain.Show;
import com.example.showservice.venue.domain.Venue;
import com.example.showservice.show.dto.SeatPriceDto;
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

    @NotNull(message = "공연장 ID는 필수 항목입니다.")
    private Long venueId;

    @NotNull(message = "장르 ID는 필수 항목입니다.")
    private Long genreId;

    @NotBlank(message = "공연자는 필수 항목입니다.")
    private String performer;

    @NotBlank(message = "공연 제목은 필수 항목입니다.")
    private String title;

    @NotBlank(message = "공연 이미지 URL은 필수 항목입니다.")
    private String titleImage; // 후.. 원래는 이미지 파일로 올려야 하는데 일단은... 요렇게하자.

    @NotNull(message = "티켓 오픈 시간은 필수 항목입니다.")
    private LocalDateTime ticketingTime;

    @NotNull(message = "공연 시작 날짜는 필수 항목입니다.")
    private LocalDateTime startDate;

    @NotNull(message = "공연 시간(분)은 필수 항목입니다.")
    private Integer duration;

    @NotEmpty(message = "좌석 가격 정보는 비어있을 수 없습니다.")
    private List<SeatPriceDto> seatPrice;

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


