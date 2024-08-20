package com.example.showservice.wishlist.service;


import com.example.servicecommon.exception.NotFoundException;
import com.example.showservice.show.domain.Show;
import com.example.showservice.show.repository.ShowRepository;
import com.example.showservice.wishlist.repository.WishlistRepository;
import com.example.showservice.wishlist.domain.Wishlist;
import com.example.showservice.wishlist.dto.WishlistPageResponse;
import com.example.showservice.wishlist.dto.WishlistResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ShowRepository showRepository;
    public WishlistResponse createWishlist(Long userId, Long showId) {
        Show show = showRepository.findById(showId).orElseThrow(() -> new NotFoundException("공연"));

        Wishlist wishlist = Wishlist.builder()
                .userId(userId)
                .show(show)
                .build();

        wishlistRepository.save(wishlist);
        return WishlistResponse.from(wishlist);
    }

    public void deleteWishlist(Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(() -> new NotFoundException("위시리스트"));
        wishlistRepository.delete(wishlist);
    }

    @Transactional(readOnly = true)
    public WishlistPageResponse findWishlist(Long userId, Pageable pageable) {
        Page<Wishlist> wishlistPage = wishlistRepository.findWishlistByUserId(userId, pageable);
        return WishlistPageResponse.from(wishlistPage);
    }
}
