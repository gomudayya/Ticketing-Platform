package com.example.orderservice.ticket.domain;

import com.example.orderservice.global.domain.BaseTimeEntity;
import com.example.orderservice.order.exception.TicketUnavailableException;
import com.example.orderservice.ticket.constant.TicketStatus;
import com.example.servicecommon.exception.CustomAccessDeniedException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

@Getter
@Entity
@SQLDelete(sql = "UPDATE ticket SET is_deleted = true WHERE id = ?")
@Where(clause = "is_Deleted = false")
@ToString
public class Ticket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Integer price;
    private String code; // code = {공연Id}_{좌석섹션}_{좌석번호}

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus = TicketStatus.AVAILABLE;
    private boolean isDeleted = false;
    protected Ticket() {
    }

    @Builder
    public Ticket(Integer price, String code) {
        this.price = price;
        this.code = code;
    }

    public static String makeCode(Long showId, String seatSection, Integer seatNumber) {
        return String.format("%d_%s_%d", showId, seatSection, seatNumber);
    }

    public void isSelectedBy(Long userId) {
        if (ticketStatus != TicketStatus.AVAILABLE) {
            throw new TicketUnavailableException();
        }

        this.userId = userId;
        ticketStatus = TicketStatus.SELECTED;
    }

    public void isPurchasedBy(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw new CustomAccessDeniedException();
        }

        this.userId = userId;
        ticketStatus = TicketStatus.SOLD;
    }

    public void makeTicketAvailable() {
        this.userId = null;
        ticketStatus = TicketStatus.AVAILABLE;
    }


    public Long getShowId() {
        return Long.parseLong(code.split("_")[0]);
    }

    public String getSeatSection() {
        return code.split("_")[1];
    }

    public Integer getSeatNumber() {
        return Integer.parseInt(code.split("_")[2]);
    }

    public boolean isOwnedBy(Long userId) {
        return this.userId.equals(userId);
    }
}
