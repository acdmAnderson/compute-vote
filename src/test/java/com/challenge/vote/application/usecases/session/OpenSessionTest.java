package com.challenge.vote.application.usecases.session;

import com.challenge.vote.VoteApplicationTests;
import com.challenge.vote.application.errors.badrequest.BadRequestException;
import com.challenge.vote.infra.repositories.memories.SessionRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OpenSessionTest implements VoteApplicationTests {

    @Autowired
    private SessionRepositoryMemory sessionRepository;

    @BeforeEach
    void setup() {
        this.sessionRepository.clean();
    }

    @Test
    void shouldOpenSession() {
        final var createSession = new CreateSession(this.sessionRepository);
        final var openSession = new OpenSession(this.sessionRepository);
        final var input = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        createSession.execute(input);
        openSession.execute(input.getSessionId());
        final var session = this.sessionRepository.findBySessionId(input.getSessionId());
        assertTrue(session.isOpen(now()));
    }

    @Test
    void shouldNotOpenSession_whenItAlreadyIsOpen() {
        final var createSession = new CreateSession(this.sessionRepository);
        final var openSession = new OpenSession(this.sessionRepository);
        final var input = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        createSession.execute(input);
        openSession.execute(input.getSessionId());
        assertThrows(BadRequestException.class, () -> openSession.execute(input.getSessionId()));
    }

    @Test
    void shouldThrow_whenSessionRepositoryThrows() {
        final var mockRepository = mock(SessionRepositoryMemory.class);
        final var openSession = new OpenSession(mockRepository);
        when(mockRepository.findBySessionId(any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> openSession.execute(1L));
    }

}
