package com.ticketland.ticketland.show.repository.querydsl;

import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.dto.ShowSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShowRepositoryQuerydsl {
    Page<Show> searchPage(ShowSearchCondition showSearchCondition, Pageable pageable);

}
