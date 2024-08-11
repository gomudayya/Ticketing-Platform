package com.ticketland.ticketland.performance.repository;

import com.ticketland.ticketland.performance.domain.Show;
import com.ticketland.ticketland.performance.repository.querydsl.ShowRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long>, ShowRepositoryQuerydsl {

}
