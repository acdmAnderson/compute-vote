package com.challenge.vote;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;


@Data
@Getter
public class Session {
    private final Long id;
    private final String description;
    private final Long duration;
    private final LocalDateTime startDate;

    public Session(Long id, String description, Long duration, LocalDateTime startDate) {
        this.id = id;
        this.description = description;
        this.duration = duration;
        this.startDate = startDate;
    }
}
