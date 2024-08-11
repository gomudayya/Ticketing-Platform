package com.ticketland.ticketland.show.repository;

import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.repository.querydsl.ShowRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long>, ShowRepositoryQuerydsl {

}
