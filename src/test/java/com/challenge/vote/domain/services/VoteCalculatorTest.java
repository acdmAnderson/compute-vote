package com.challenge.vote.domain.services;

import com.challenge.vote.domain.entities.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VoteCalculatorTest {

    @Test
    void shouldCalculateVotes() {
        final var votes = List.of(
                new Vote(1L, 1L, "ANY_CPF", TRUE, now()),
                new Vote(2L, 1L, "SOME_CPF", FALSE, now()),
                new Vote(3L, 1L, "ANOTHER_CPF", TRUE, now())
        );
        final var voteCalculator = new VoteCalculator(votes);
        assertEquals(voteCalculator.getTotal(), 3L);
        assertEquals(voteCalculator.getInFavor(), 2L);
        assertEquals(voteCalculator.getNotInFavor(), 1L);
    }
}
