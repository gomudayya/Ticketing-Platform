package com.ticketland.ticketland.wishlist.dto;

import com.ticketland.ticketland.global.dto.PageMetaDto;
import com.ticketland.ticketland.wishlist.domain.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistPageResponse {

    private PageMetaDto pageMeta;
    private List<WishlistResponse> content;

    public static WishlistPageResponse from(Page<Wishlist> wishlistPage) {
        List<WishlistResponse> content = wishlistPage.getContent().stream()
                .map(WishlistResponse::from)
                .toList();

        return builder()
                .pageMeta(PageMetaDto.from(wishlistPage))
                .content(content)
                .build();
    }
}
