package com.example.orderservice.ticket.repository;

import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.repository.querydsl.TicketRepositoryQuerydsl;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, String>, TicketRepositoryQuerydsl {

    Optional<Ticket> findByCode(String code);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Ticket t WHERE t.code = :code")
    Optional<Ticket> findByCodeWithLock(@Param("code") String code);

    @Query("SELECT t FROM Ticket t WHERE t.code LIKE CONCAT(:showId, '\\_%') ESCAPE '\\'")
    List<Ticket> findTicketByShowId(@Param("showId") Long showId);
    List<Ticket> findByIdIn(List<Long> ticketIds);

}
