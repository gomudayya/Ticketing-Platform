package com.example.userservice.wishlist.controller;

import com.example.servicecommon.auth.AllowedAuthority;
import com.example.servicecommon.auth.UserRole;
import com.example.servicecommon.dto.UserClaim;
import com.example.servicecommon.resolver.AuthPrincipal;
import com.example.userservice.wishlist.dto.WishlistPageResponse;
import com.example.userservice.wishlist.dto.WishlistResponse;
import com.example.userservice.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/show/{showId}")
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<WishlistResponse> createWishlist(@AuthPrincipal UserClaim userClaim, @PathVariable Long showId) {
        return ResponseEntity.ok(wishlistService.createWishlist(userClaim.getUserId(), showId));
    }

    @DeleteMapping("/{wishlistId}")
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<?> deleteWishlist(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlist(wishlistId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<WishlistPageResponse> findWishlist(@AuthPrincipal UserClaim userClaim, Pageable pageable) {
        return ResponseEntity.ok(wishlistService.findMyWishlist(userClaim.getUserId(), pageable));
    }
}
