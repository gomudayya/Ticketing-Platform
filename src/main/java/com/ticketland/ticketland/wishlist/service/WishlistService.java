package com.ticketland.ticketland.wishlist.service;


import com.ticketland.ticketland.global.exception.NotFoundException;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.repository.ShowRepository;
import com.ticketland.ticketland.user.domain.User;
import com.ticketland.ticketland.user.repository.UserRepository;
import com.ticketland.ticketland.wishlist.domain.Wishlist;
import com.ticketland.ticketland.wishlist.dto.WishlistPageResponse;
import com.ticketland.ticketland.wishlist.dto.WishlistResponse;
import com.ticketland.ticketland.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;

    public WishlistResponse createWishlist(Long userId, Long showId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("회원"));
        Show show = showRepository.findById(showId).orElseThrow(() -> new NotFoundException("공연"));

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .show(show)
                .build();

        wishlistRepository.save(wishlist);
        return WishlistResponse.from(wishlist);
    }

    public void deleteWishlist(Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(() -> new NotFoundException("위시리스트"));
        wishlistRepository.delete(wishlist);
    }

    public WishlistPageResponse findWishlist(Long userId, Pageable pageable) {
        Page<Wishlist> wishlistPage = wishlistRepository.findWishlistByUserId(userId, pageable);
        return WishlistPageResponse.from(wishlistPage);
    }
}
