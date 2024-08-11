package com.ticketland.ticketland.show.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ticketland.ticketland.show.constant.ShowStatus;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.dto.ShowSearchCondition;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static com.ticketland.ticketland.show.constant.ShowConstant.TICKET_PURCHASE_OFFSET_HOURS;
import static com.ticketland.ticketland.show.domain.QShow.show;

@RequiredArgsConstructor
public class ShowRepositoryQuerydslImpl implements ShowRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;
    @Override
    public Slice<Show> searchPage(ShowSearchCondition condition, Pageable pageable) {
        List<Show> content = queryFactory.selectFrom(show)
                .leftJoin(show.genre).fetchJoin()
                .where(
                        containsTitle(condition.getTitle()),
                        containsPerformer(condition.getPerformer()),
                        containsGenre(condition.getGenre()),
                        statusFilter(condition.getShowStatus())
                )
                .orderBy(show.ticketingTime.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 무한스크롤 방식은 한개 더 받아옴
                .fetch();

        boolean hasNext = content.size() > pageable.getPageSize(); // 다음 페이지 여부 확인
        if (hasNext) content.remove(content.size()-1); // 다음페이지가 있는걸 확인했으면 마지막 항목 확인

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression containsTitle(String title) {
        return StringUtils.isEmpty(title) ? null : show.title.containsIgnoreCase(title);
    }

    private BooleanExpression containsPerformer(String performer) {
        return StringUtils.isEmpty(performer) ? null : show.performer.containsIgnoreCase(performer);
    }

    private BooleanExpression containsGenre(String genre) {
        return StringUtils.isEmpty(genre) ? null : show.genre.genreName.containsIgnoreCase(genre);
    }

    private BooleanExpression statusFilter(ShowStatus status) {
        if (status == null) return null;

        LocalDateTime now = LocalDateTime.now();
        switch (status) {
            case BEFORE_TICKET_OPEN:
                return isBeforeTicketOpen(now);
            case TICKET_SALE_ENDED:
                return isTicketSaleEnded(now);
            case TICKET_SALE_ACTIVE:
                return isTicketSaleActive(now);
            default:
                throw new IllegalStateException("Unexpected value: " + status);
        }
    }

    private BooleanExpression isBeforeTicketOpen(LocalDateTime now) {
        return show.ticketingTime.gt(now); // 티켓팅시간이 현재시간보다 이후이다.
    }

    private BooleanExpression isTicketSaleEnded(LocalDateTime now) {
        // ex)공연시작시간 3시간전까지가 티켓 구매 가능시간이다. (시작시간 < 현재시간 + 3시간) => 티켓 종료시간.
        LocalDateTime time = now.plusHours(TICKET_PURCHASE_OFFSET_HOURS);
        return show.startTime.loe(time);
    }

    private BooleanExpression isTicketSaleActive(LocalDateTime now) {
        return isBeforeTicketOpen(now).not().and(isTicketSaleEnded(now).not()); // 티켓오픈시간 이후이고, 티켓판매가 종료된것도 아니면 ACTIVE
    }
}
