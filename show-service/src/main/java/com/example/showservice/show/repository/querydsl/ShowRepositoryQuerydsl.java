package com.example.showservice.show.repository.querydsl;

import com.example.showservice.show.dto.show.ShowSearchCondition;
import com.example.showservice.show.domain.Show;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ShowRepositoryQuerydsl {
    Slice<Show> searchPage(ShowSearchCondition showSearchCondition, Pageable pageable);

}
