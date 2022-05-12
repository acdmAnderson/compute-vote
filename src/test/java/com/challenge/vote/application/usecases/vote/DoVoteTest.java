package com.challenge.vote.application.usecases.vote;

import com.challenge.vote.application.usecases.session.CreateSession;
import com.challenge.vote.application.usecases.session.CreateSessionInput;
import com.challenge.vote.infra.repositories.memories.SessionRepositoryMemory;
import com.challenge.vote.infra.repositories.memories.VoteRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DoVoteTest {
    @Autowired
    private SessionRepositoryMemory sessionRepository;

    @Autowired
    private VoteRepositoryMemory voteRepositoryMemory;

    @BeforeEach
    void setup() {
        this.sessionRepository.clean();
        this.voteRepositoryMemory.clean();
    }

    @Test
    void shouldDoVote() throws Exception {
        final var sessionInput = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createSession = new CreateSession(sessionRepository);
        createSession.execute(sessionInput);
        final var doVote = new DoVote(sessionRepository, voteRepositoryMemory);
        final var firstInputVote = DoVoteInput.builder()
                .cpf("ANY_CPF")
                .id(1L)
                .idSession(1L)
                .inFavor(TRUE)
                .build();
        doVote.execute(firstInputVote);
        final var secondInputVote = DoVoteInput.builder()
                .cpf("ANOTHER_CPF")
                .id(2L)
                .idSession(1L)
                .inFavor(FALSE)
                .build();
        doVote.execute(secondInputVote);
        final var votes = voteRepositoryMemory.findByIdSession(1L);
        assertEquals(votes.size(), 2L);
    }
}
