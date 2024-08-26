package com.example.showservice.venue.dto;

import com.example.showservice.global.dto.PageMetaDto;
import com.example.showservice.venue.domain.Venue;
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
    private PageMetaDto pageMeta;
    private List<VenueSimpleResponse> content;

    public static VenuePageResponse from(Page<Venue> venuePage) {
        List<VenueSimpleResponse> findContent = venuePage.getContent().stream()
                .map(VenueSimpleResponse::from)
                .toList();

        return builder()
                .pageMeta(PageMetaDto.from(venuePage))
                .content(findContent)
                .build();
    }
}
