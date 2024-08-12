package com.ticketland.ticketland.show.dto.show;

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
}


