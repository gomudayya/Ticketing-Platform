package com.example.showservice.wishlist.repository;

import com.example.showservice.wishlist.repository.querydsl.WishlistRepositoryQuerydsl;
import com.example.showservice.wishlist.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>, WishlistRepositoryQuerydsl {
}
