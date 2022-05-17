package com.challenge.vote.application.usecases.vote;

import com.challenge.vote.VoteApplicationTests;
import com.challenge.vote.application.errors.badrequest.BadRequestException;
import com.challenge.vote.application.usecases.session.CreateSession;
import com.challenge.vote.application.usecases.session.CreateSessionInput;
import com.challenge.vote.application.usecases.session.OpenSession;
import com.challenge.vote.infra.repositories.memories.SessionRepositoryMemory;
import com.challenge.vote.infra.repositories.memories.VoteRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DoVoteTest implements VoteApplicationTests {

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
    void shouldDoVote() {
        final var createSession = new CreateSession(sessionRepository);
        final var openSession = new OpenSession(sessionRepository);
        final var doVote = new DoVote(sessionRepository, voteRepositoryMemory);
        final var sessionInput = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var firstInputVote = DoVoteInput.builder()
                .cpf("ANY_CPF")
                .id(1L)
                .sessionId(1L)
                .inFavor(TRUE)
                .build();
        final var secondInputVote = DoVoteInput.builder()
                .cpf("ANOTHER_CPF")
                .id(2L)
                .sessionId(1L)
                .inFavor(FALSE)
                .build();
        final var session = createSession.execute(sessionInput);
        openSession.execute(session.getSessionId());
        doVote.execute(firstInputVote);
        doVote.execute(secondInputVote);
        final var votes = voteRepositoryMemory.findBySessionId(1L);
        assertEquals(votes.size(), 2L);
    }

    @Test
    void shouldThrow_whenSessionIsNotOpen() {
        final var createSession = new CreateSession(sessionRepository);
        final var doVote = new DoVote(sessionRepository, voteRepositoryMemory);
        final var sessionInput = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var firstInputVote = DoVoteInput.builder()
                .cpf("ANY_CPF")
                .id(1L)
                .sessionId(1L)
                .inFavor(TRUE)
                .build();
        createSession.execute(sessionInput);
        assertThrows(BadRequestException.class, () -> doVote.execute(firstInputVote));
    }

    @Test
    void shouldThrow_whenUserAlreadyVoted() {
        final var createSession = new CreateSession(sessionRepository);
        final var openSession = new OpenSession(sessionRepository);
        final var doVote = new DoVote(sessionRepository, voteRepositoryMemory);
        final var sessionInput = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var input = DoVoteInput.builder()
                .cpf("ANY_CPF")
                .id(1L)
                .sessionId(sessionInput.getSessionId())
                .inFavor(TRUE)
                .build();
        final var session = createSession.execute(sessionInput);
        openSession.execute(session.getSessionId());
        doVote.execute(input);
        assertThrows(BadRequestException.class, () -> doVote.execute(input));
    }

    @Test
    void shouldThrow_whenSessionRepositoryThrows() {
        final var input = DoVoteInput.builder()
                .cpf("ANY_CPF")
                .id(1L)
                .sessionId(1L)
                .inFavor(TRUE)
                .build();
        final var mockRepository = mock(SessionRepositoryMemory.class);
        final var computeVote = new DoVote(mockRepository, voteRepositoryMemory);
        when(mockRepository.findBySessionId(any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> computeVote.execute(input));
    }
}
