package com.example.showservice.wishlist.repository.querydsl;

import com.example.showservice.wishlist.domain.Wishlist;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.showservice.wishlist.domain.QWishlist.wishlist;

@RequiredArgsConstructor
public class WishlistRepositoryQuerydslImpl implements WishlistRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Wishlist> findWishlistByUserId(Long userId, Pageable pageable) {
        List<Wishlist> content = queryFactory.selectFrom(wishlist)
                .leftJoin(wishlist.show).fetchJoin()
                .leftJoin(wishlist.show.genre).fetchJoin()
                .where(wishlist.userId.eq(userId).and(wishlist.isDeleted.eq(false)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(wishlist.count()).from(wishlist)
                .where(wishlist.userId.eq(userId)
                        .and(wishlist.isDeleted.eq(false)))
                .fetchFirst();

        return new PageImpl<>(content, pageable, total);
    }
}
