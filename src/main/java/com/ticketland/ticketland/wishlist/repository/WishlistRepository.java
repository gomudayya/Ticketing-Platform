package com.ticketland.ticketland.wishlist.repository;

import com.ticketland.ticketland.wishlist.domain.Wishlist;
import com.ticketland.ticketland.wishlist.repository.querydsl.WishlistRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>, WishlistRepositoryQuerydsl {
}
