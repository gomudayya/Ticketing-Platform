package com.ticketland.ticketland.show.repository.querydsl;

import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.dto.ShowSearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ShowRepositoryQuerydsl {
    Slice<Show> searchPage(ShowSearchCondition showSearchCondition, Pageable pageable);

}
