package com.example.showservice.wishlist.repository.querydsl;

import com.example.showservice.wishlist.domain.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistRepositoryQuerydsl {
    Page<Wishlist> findWishlistByUserId(Long userId, Pageable pageable);
}
