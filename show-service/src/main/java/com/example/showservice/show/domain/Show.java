package com.example.showservice.show.domain;

import com.example.showservice.genre.domain.Genre;
import com.example.showservice.global.domain.BaseTimeEntity;
import com.example.showservice.show.constant.ShowStatus;
import com.example.showservice.venue.domain.Venue;
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

import static com.example.showservice.show.constant.ShowConstant.PURCHASE_DEADLINE_HOURS_BEFORE_SHOW;

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
    private List<ShowSeat> showSeats = new ArrayList<>();

    private String performer;
    private String title;
    private String titleImage;
    private LocalDateTime ticketOpenTime;
    private LocalDateTime startTime;
    private Integer duration; // 공연시간 (분)
    private boolean isDeleted = false;

    @Builder
    public Show(Genre genre, Venue venue, String performer, String title, String titleImage,
                LocalDateTime ticketOpenTime, LocalDateTime startTime, Integer duration) {
        this.genre = genre;
        this.venue = venue;
        this.performer = performer;
        this.title = title;
        this.titleImage = titleImage;
        this.ticketOpenTime = ticketOpenTime;
        this.startTime = startTime;
        this.duration = duration;
    }

    protected Show(){}

    public String getGenreName() {
        return genre.getGenreName();
    }

    public ShowStatus getShowStatus() {
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(ticketOpenTime)) {
            return ShowStatus.BEFORE_TICKET_OPEN;
        }
        if (now.isAfter(startTime.minusHours(PURCHASE_DEADLINE_HOURS_BEFORE_SHOW))) {
            return ShowStatus.TICKET_SALE_ENDED;
        }
        else return ShowStatus.TICKET_SALE_ACTIVE;
    }
}
