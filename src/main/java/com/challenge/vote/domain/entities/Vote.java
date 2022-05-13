package com.challenge.vote.domain.entities;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Getter
@Entity(name = "vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private Boolean inFavor;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Vote() {
    }

    public Vote(Long id, Long sessionId, String cpf, Boolean inFavor, LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.cpf = cpf;
        this.inFavor = inFavor;
        this.createdAt = createdAt;
    }
}
