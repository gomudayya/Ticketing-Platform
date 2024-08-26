package com.example.userservice.wishlist.repository.querydsl;

import com.example.userservice.user.domain.User;
import com.example.userservice.wishlist.domain.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistRepositoryQuerydsl {
    Page<Wishlist> findWishlistByUser(User user, Pageable pageable);
}
