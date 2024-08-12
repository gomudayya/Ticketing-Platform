package com.ticketland.ticketland.wishlist.dto;

import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.dto.show.ShowSingleResponse;
import com.ticketland.ticketland.wishlist.domain.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {
    private Long wishlistId;
    private ShowSingleResponse show;

    public static WishlistResponse from(Wishlist wishlist) {
        Show findShow = wishlist.getShow();

        return builder()
                .wishlistId(wishlist.getId())
                .show(ShowSingleResponse.from(findShow))
                .build();
    }
}
