package com.challenge.vote.application.usecases.session;

import com.challenge.vote.VoteApplicationTests;
import com.challenge.vote.infra.repositories.memories.SessionRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateSessionTest implements VoteApplicationTests {

    @Autowired
    private SessionRepositoryMemory sessionRepository;

    @BeforeEach
    void setup() {
        this.sessionRepository.clean();
    }

    @Test
    void shouldCreateSession() {
        final var input = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createSession = new CreateSession(this.sessionRepository);
        createSession.execute(input);
        final var sessions = this.sessionRepository.findAll();
        assertEquals(sessions.size(), 1);
    }

    @Test
    void shouldThrow_whenSessionRepositoryThrows() {
        final var input = CreateSessionInput.builder()
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var mockRepository = Mockito.mock(SessionRepositoryMemory.class);
        final var createSession = new CreateSession(mockRepository);
        Mockito.when(mockRepository.save(ArgumentMatchers.any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> createSession.execute(input));
    }
}
