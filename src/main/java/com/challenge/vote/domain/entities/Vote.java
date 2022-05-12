package com.challenge.vote.domain.entities;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class Vote {
    private final Long id;
    private final Long idSession;
    private final String cpf;
    private final Boolean inFavor;
    private final LocalDateTime createdAt;

    public Vote(Long id, Long idSession, String cpf,Boolean inFavor, LocalDateTime createdAt) {
        this.id = id;
        this.idSession = idSession;
        this.cpf = cpf;
        this.inFavor = inFavor;
        this.createdAt = createdAt;
    }
}
