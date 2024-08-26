package com.example.userservice.wishlist.repository.querydsl;

import com.example.userservice.user.domain.User;
import com.example.userservice.wishlist.domain.QWishlist;
import com.example.userservice.wishlist.domain.Wishlist;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class WishlistRepositoryQuerydslImpl implements WishlistRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Wishlist> findWishlistByUser(User user, Pageable pageable) {
        List<Wishlist> content = queryFactory.selectFrom(QWishlist.wishlist)
                .where(QWishlist.wishlist.user.eq(user).and(QWishlist.wishlist.isDeleted.eq(false)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(QWishlist.wishlist.count()).from(QWishlist.wishlist)
                .where(QWishlist.wishlist.user.eq(user)
                        .and(QWishlist.wishlist.isDeleted.eq(false)))
                .fetchFirst();

        return new PageImpl<>(content, pageable, total);
    }
}
