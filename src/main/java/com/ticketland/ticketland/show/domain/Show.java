package com.ticketland.ticketland.show.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "shows")
@SQLDelete(sql = "UPDATE shows SET is_deleted = true WHERE id = ?")
@Where(clause = "is_Deleted = 0")
public class Show extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(mappedBy = "show")
    private List<TicketPrice> ticketPrices = new ArrayList<>();

    private String performer;
    private String title;
    private String descriptionImage;
    private LocalDateTime ticketingTime;
    private LocalDateTime startTime;
    private Integer duration; // 공연시간 (분)
    private boolean isDeleted = false;

    @Builder
    public Show(Genre genre, Venue venue, String performer, String title, String descriptionImage,
                LocalDateTime ticketingTime, LocalDateTime startTime, Integer duration) {
        this.genre = genre;
        this.venue = venue;
        this.performer = performer;
        this.title = title;
        this.descriptionImage = descriptionImage;
        this.ticketingTime = ticketingTime;
        this.startTime = startTime;
        this.duration = duration;
    }

    protected Show(){}

    public String getGenreName() {
        return genre.getGenreName();
    }

}
