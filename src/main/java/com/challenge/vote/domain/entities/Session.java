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
    private final Long id;

    @Column(nullable = false, unique = true)
    private final String description;

    @Column(nullable = false)
    private final Long duration;

    @Column(name = "start_data")
    private LocalDateTime startDate;

    public Session(Long id, String description, Long duration) {
        this.id = id;
        this.description = description;
        this.duration = duration;
    }

    public Session(Long id, String description) {
        this.id = id;
        this.description = description;
        this.duration = 60L;
    }

    public void open(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Boolean isOpen(LocalDateTime currentDate) {
        if (isNull(startDate)) return FALSE;
        return currentDate.isBefore(this.startDate.plus(duration, SECONDS));
    }
}
