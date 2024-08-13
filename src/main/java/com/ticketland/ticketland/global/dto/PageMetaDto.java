package com.ticketland.ticketland.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageMetaDto {
    private Integer currentPage;
    private Integer lastPage;
    private Long totalContent;
    private Integer pageSize;

    public static PageMetaDto from(Page<?> page) {
        return builder()
                .currentPage(page.getNumber())
                .lastPage(page.getTotalPages() -1)
                .totalContent(page.getTotalElements())
                .pageSize(page.getSize())
                .build();
    }
}
