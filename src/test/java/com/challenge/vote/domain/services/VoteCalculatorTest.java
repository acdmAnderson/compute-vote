package com.challenge.vote.domain.services;

import com.challenge.vote.domain.entities.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class VoteCalculatorTest {

    @Test
    void shouldCalculateVotes() {
        final var votes = List.of(
                new Vote(1L, 1L, "ANY_CPF", Boolean.TRUE, LocalDateTime.now()),
                new Vote(2L, 1L, "SOME_CPF", Boolean.FALSE, LocalDateTime.now()),
                new Vote(3L, 1L, "ANOTHER_CPF", Boolean.TRUE, LocalDateTime.now())
        );
        final var voteCalculator = new VoteCalculator(votes);
        Assertions.assertEquals(voteCalculator.getTotal(), 3L);
        Assertions.assertEquals(voteCalculator.getInFavor(), 2L);
        Assertions.assertEquals(voteCalculator.getNotInFavor(), 1L);
    }
}
