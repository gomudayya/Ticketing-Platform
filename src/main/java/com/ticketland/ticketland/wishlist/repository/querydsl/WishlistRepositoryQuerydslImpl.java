package com.ticketland.ticketland.wishlist.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ticketland.ticketland.wishlist.domain.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ticketland.ticketland.wishlist.domain.QWishlist.wishlist;

@RequiredArgsConstructor
public class WishlistRepositoryQuerydslImpl implements WishlistRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Wishlist> findWishlistByUserId(Long userId, Pageable pageable) {
        List<Wishlist> content = queryFactory.selectFrom(wishlist)
                .leftJoin(wishlist.show).fetchJoin() // user 관련해서는 필요 없으므로 공연관련해서만 fetchJoin
                .leftJoin(wishlist.show.genre).fetchJoin()
                .where(wishlist.user.id.eq(userId).and(wishlist.isDeleted.eq(false)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(wishlist.count()).from(wishlist)
                .where(wishlist.user.id.eq(userId)
                        .and(wishlist.isDeleted.eq(false)))
                .fetchFirst();

        return new PageImpl<>(content, pageable, total);
    }
}
