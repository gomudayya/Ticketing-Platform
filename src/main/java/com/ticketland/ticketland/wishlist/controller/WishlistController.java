package com.ticketland.ticketland.wishlist.controller;

import com.ticketland.ticketland.user.constant.UserRole;
import com.ticketland.ticketland.wishlist.dto.WishlistResponse;
import com.ticketland.ticketland.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
}
