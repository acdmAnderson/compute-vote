package com.challenge.vote.infra.repositories.databases;

import com.challenge.vote.domain.entities.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class VoteRepositoryDatabaseTest {

    @Autowired
    private VoteRepositoryDatabase voteRepositoryDatabase;

    @Test
    void shouldCreateVote() {
        final var vote = new Vote(null, 1L, "ANY_CPF", Boolean.TRUE, LocalDateTime.parse("2021-10-10T10:00:00"));
        final var savedVote = this.voteRepositoryDatabase.save(vote);
        Assertions.assertEquals(savedVote.getSessionId(), 1L);
        Assertions.assertEquals(savedVote.getCpf(), "ANY_CPF");
        Assertions.assertEquals(savedVote.getInFavor(), Boolean.TRUE);
        Assertions.assertEquals(savedVote.getCreatedAt(), LocalDateTime.parse("2021-10-10T10:00:00"));
    }
}
