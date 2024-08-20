package com.example.showservice.wishlist.dto;

import com.example.showservice.show.dto.show.ShowSimpleResponse;
import com.example.showservice.wishlist.domain.Wishlist;
import com.example.showservice.show.domain.Show;
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
    private ShowSimpleResponse show;

    public static WishlistResponse from(Wishlist wishlist) {
        Show findShow = wishlist.getShow();

        return builder()
                .wishlistId(wishlist.getId())
                .show(ShowSimpleResponse.from(findShow))
                .build();
    }
}
