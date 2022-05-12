package com.challenge.vote.domain.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class VoteTest {

    @Test
    void shouldCreateVote() {
        final var vote = new Vote(1L, 1L,"ANY_CPF", LocalDateTime.parse("2020-01-01T10:00:00"));
        Assertions.assertEquals(vote.getIdSession(), 1L);
        Assertions.assertEquals(vote.getId(), 1L);
        Assertions.assertEquals(vote.getCpf(), "ANY_CPF");
        Assertions.assertEquals(vote.getCreatedAt(), LocalDateTime.parse("2020-01-01T10:00:00"));
    }
}
