package com.ticketland.ticketland.show.dto.venue;

import com.ticketland.ticketland.show.domain.Venue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class VenuePageResponse {
    private Integer currentPage;
    private Integer lastPage;
    private Long totalContent;
    private Integer pageSize;
    private List<VenueSimpleResponse> content;

    public static VenuePageResponse from(Page<Venue> venuePage) {
        List<VenueSimpleResponse> findContent = venuePage.getContent().stream()
                .map(VenueSimpleResponse::from)
                .toList();

        return builder()
                .currentPage(venuePage.getNumber())
                .lastPage(venuePage.getTotalPages() -1)
                .totalContent(venuePage.getTotalElements())
                .pageSize(venuePage.getSize())
                .content(findContent)
                .build();
    }
}
