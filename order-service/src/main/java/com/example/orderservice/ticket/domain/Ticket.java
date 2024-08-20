package com.example.orderservice.ticket.domain;

import com.example.orderservice.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@SQLDelete(sql = "UPDATE ticket SET is_deleted = true WHERE id = ?")
@Where(clause = "is_Deleted = 0")
public class Ticket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer price;
    private String code; // code = {공연Id}_{좌석섹션}_{좌석번호}
    private boolean isSold = false;
    private boolean isDeleted = false;

    protected Ticket() {}

    @Builder
    public Ticket(Integer price, String code) {
        this.price = price;
        this.code = code;
    }

    public static String makeCode(Long showId, String seatSection, Integer seatNumber) {
        return String.format("%d_%s_%d", showId, seatSection, seatNumber);
    }

    public void bePurchased() {
        isSold = true;
    }

    public void beRefunded() {
        isSold = false;
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

}
