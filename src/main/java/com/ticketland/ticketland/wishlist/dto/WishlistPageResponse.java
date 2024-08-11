package com.ticketland.ticketland.wishlist.dto;

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
    
    private Integer currentPage;
    private Integer lastPage;
    private Integer totalContent;
    private Integer pageSize;
    private List<WishlistResponse> content;

    public static WishlistPageResponse from(Page<Wishlist> wishlistPage) {
        List<WishlistResponse> content = wishlistPage.getContent().stream()
                .map(WishlistResponse::from)
                .toList();

        return builder()
                .currentPage(wishlistPage.getNumber())
                .lastPage(wishlistPage.getTotalPages() - 1)
                .pageSize(wishlistPage.getSize())
                .totalContent(wishlistPage.getTotalPages())
                .content(content)
                .build();
    }
}
