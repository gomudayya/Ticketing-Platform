package com.example.userservice.wishlist.dto;

import com.example.userservice.client.showservice.dto.ShowSimpleResponse;
import com.example.userservice.global.dto.PageMetaDto;
import com.example.userservice.wishlist.domain.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistPageResponse {

    private PageMetaDto pageMeta;
    private List<WishlistResponse> content;

    public static WishlistPageResponse from(Page<Wishlist> wishlistPage, List<ShowSimpleResponse> shows) {
        Map<Long, ShowSimpleResponse> showMap = shows.stream()
                .collect(Collectors.toMap(ShowSimpleResponse::getShowId, show -> show));

        List<WishlistResponse> content = wishlistPage.getContent().stream()
                .map(wishlist -> WishlistResponse.from(wishlist, showMap.get(wishlist.getShowId())))
                .toList();

        return builder()
                .pageMeta(PageMetaDto.from(wishlistPage))
                .content(content)
                .build();
    }
}
