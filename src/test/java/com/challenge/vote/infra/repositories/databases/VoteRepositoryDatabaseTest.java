package com.challenge.vote.infra.repositories.databases;

import com.challenge.vote.domain.entities.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class VoteRepositoryDatabaseTest {

    @Autowired
    private VoteRepositoryDatabase voteRepositoryDatabase;

    @BeforeEach
    void setup() {
        this.voteRepositoryDatabase.clean();
    }

    @Test
    void shouldCreateVote() {
        final var vote = new Vote(null, 1L, "ANY_CPF", TRUE, parse("2021-10-10T10:00:00"));
        final var savedVote = this.voteRepositoryDatabase.save(vote);
        assertEquals(savedVote.getSessionId(), 1L);
        assertEquals(savedVote.getCpf(), "ANY_CPF");
        assertEquals(savedVote.getInFavor(), TRUE);
        assertEquals(savedVote.getCreatedAt(), parse("2021-10-10T10:00:00"));
    }

    @Test
    void shouldGetVoteBySessionId() {
        final var vote = new Vote(null, 1L, "ANY_CPF", TRUE, parse("2021-10-10T10:00:00"));
        this.voteRepositoryDatabase.save(vote);
        final var votes = this.voteRepositoryDatabase.findBySessionId(1L);
        assertEquals(votes.size(), 1L);
        assertEquals(votes.get(0).getSessionId(), 1L);
        assertEquals(votes.get(0).getCpf(), "ANY_CPF");
        assertEquals(votes.get(0).getInFavor(), TRUE);
        assertEquals(votes.get(0).getCreatedAt(), parse("2021-10-10T10:00:00"));
    }

    @Test
    void shouldGetVoteBySessionIdAndCpf() {
        final var vote = new Vote(null, 1L, "ANY_CPF", TRUE, parse("2021-10-10T10:00:00"));
        this.voteRepositoryDatabase.save(vote);
        final var foundVote = this.voteRepositoryDatabase.findBySessionIdAndCpf(1L, "ANY_CPF");
        assertEquals(foundVote.getSessionId(), 1L);
        assertEquals(foundVote.getCpf(), "ANY_CPF");
        assertEquals(foundVote.getInFavor(), TRUE);
        assertEquals(foundVote.getCreatedAt(), parse("2021-10-10T10:00:00"));
    }

    @Test
    void shouldNotGetVote_whenSessionIdNotExists() {
        final var votes = this.voteRepositoryDatabase.findBySessionId(2L);
        assertTrue(votes.isEmpty());
    }
}
