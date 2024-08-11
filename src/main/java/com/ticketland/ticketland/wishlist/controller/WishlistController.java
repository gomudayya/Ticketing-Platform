package com.ticketland.ticketland.wishlist.controller;

import com.ticketland.ticketland.user.constant.UserRole;
import com.ticketland.ticketland.wishlist.dto.WishlistPageResponse;
import com.ticketland.ticketland.wishlist.dto.WishlistResponse;
import com.ticketland.ticketland.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @Secured(UserRole.Authority.USER)
    @PostMapping("/{showId}")
    public ResponseEntity<WishlistResponse> createWishlist(@AuthenticationPrincipal Long userId, @PathVariable Long showId) {
        return ResponseEntity.ok(wishlistService.createWishlist(userId, showId));
    }

    @Secured(UserRole.Authority.USER)
    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<?> deleteWishlist(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlist(wishlistId);
        return ResponseEntity.noContent().build();
    }

    @Secured(UserRole.Authority.USER)
    @GetMapping
    public ResponseEntity<WishlistPageResponse> findWishlist(@AuthenticationPrincipal Long userId, Pageable pageable) {
        return ResponseEntity.ok(wishlistService.findWishlist(userId, pageable));
    }
}
