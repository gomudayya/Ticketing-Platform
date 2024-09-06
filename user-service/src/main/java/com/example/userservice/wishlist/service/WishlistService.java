package com.example.userservice.wishlist.service;


import com.example.servicecommon.exception.NotFoundException;
import com.example.userservice.client.showservice.ShowServiceClient;
import com.example.userservice.client.showservice.dto.ShowSimpleResponse;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.service.UserService;
import com.example.userservice.wishlist.exception.DuplicatedWishlistItemException;
import com.example.userservice.wishlist.repository.WishlistRepository;
import com.example.userservice.wishlist.domain.Wishlist;
import com.example.userservice.wishlist.dto.WishlistPageResponse;
import com.example.userservice.wishlist.dto.WishlistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserService userService;
    private final ShowServiceClient showServiceClient;
    public WishlistResponse createWishlist(Long userId, Long showId) {
        ShowSimpleResponse findShow = showServiceClient.getShow(showId);
        User user = userService.findUserById(userId);
        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .showId(findShow.getShowId())
                .build();

        if (wishlistRepository.existsByUserIdAndShowId(userId, showId)) {
            throw new DuplicatedWishlistItemException();
        }

        wishlistRepository.save(wishlist);
        return WishlistResponse.from(wishlist, findShow);
    }

    public void deleteWishlist(Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(() -> new NotFoundException("위시리스트"));
        wishlistRepository.delete(wishlist);
    }

    @Transactional(readOnly = true)
    public WishlistPageResponse findMyWishlist(Long userId, Pageable pageable) {
        User user = userService.findUserById(userId);
        Page<Wishlist> wishlistPage = wishlistRepository.findWishlistByUser(user, pageable);

        List<Long> showIds = wishlistPage.getContent().stream().map(Wishlist::getShowId).toList();
        List<ShowSimpleResponse> shows = showServiceClient.getShows(showIds);

        return WishlistPageResponse.from(wishlistPage, shows);
    }
}
