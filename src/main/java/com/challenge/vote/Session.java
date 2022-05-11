package com.challenge.vote;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Objects.isNull;


@Data
@Getter
public class Session {
    private final Long id;
    private final String description;
    private final Long duration;
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
