package com.example.userservice.wishlist.dto;

import com.example.userservice.client.showservice.dto.ShowSimpleResponse;
import com.example.userservice.wishlist.domain.Wishlist;
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

    public static WishlistResponse from(Wishlist wishlist, ShowSimpleResponse show) {
        return builder()
                .wishlistId(wishlist.getId())
                .show(show)
                .build();
    }
}
