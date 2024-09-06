package com.example.userservice.wishlist.repository;

import com.example.userservice.wishlist.repository.querydsl.WishlistRepositoryQuerydsl;
import com.example.userservice.wishlist.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>, WishlistRepositoryQuerydsl {

    boolean existsByUserIdAndShowId(Long userId, Long showId);
}
