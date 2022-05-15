package com.challenge.vote.domain.entities;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Objects.isNull;


@Data
@Getter
@Entity(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String description;

    @Column(nullable = false)
    private Long duration = 60L;

    @Column(name = "start_data")
    private LocalDateTime startDate;

    public Session() {
    }

    public Session(Long id, String description, Long duration) {
        this.id = id;
        this.description = description;
        if (!isNull(duration)) {
            this.duration = duration;
        }
    }

    public Session(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public void open(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Boolean isOpen(LocalDateTime currentDate) {
        if (isNull(startDate)) return FALSE;
        return currentDate.isBefore(this.startDate.plus(duration, SECONDS));
    }
}
