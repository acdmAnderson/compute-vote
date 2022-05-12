package com.challenge.vote.domain.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static java.time.LocalDateTime.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VoteTest {

    @Test
    void shouldCreateVote() {
        final var vote = new Vote(1L, 1L,"ANY_CPF", parse("2020-01-01T10:00:00"));
        assertEquals(vote.getIdSession(), 1L);
        assertEquals(vote.getId(), 1L);
        assertEquals(vote.getCpf(), "ANY_CPF");
        assertEquals(vote.getCreatedAt(), parse("2020-01-01T10:00:00"));
    }
}
