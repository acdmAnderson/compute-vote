package com.challenge.vote.application.usecases.session;

import com.challenge.vote.VoteApplicationTests;
import com.challenge.vote.application.errors.notfound.NotFoundException;
import com.challenge.vote.infra.repositories.memories.SessionRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetSessionTest implements VoteApplicationTests {

    @Autowired
    private SessionRepositoryMemory sessionRepository;

    @BeforeEach
    void setup() {
        this.sessionRepository.clean();
    }

    @Test
    void shouldGetSession() throws NotFoundException {
        final var createSession = new CreateSession(this.sessionRepository);
        final var getSession = new GetSession(this.sessionRepository);
        final var input = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        createSession.execute(input);
        final var output = getSession.execute(input.getSessionId());
        assertEquals(output.getSessionId(), 1L);
        assertEquals(output.getSessionDescription(), "ANY_SESSION");
        assertEquals(output.getSessionDuration(), 3600L);
    }

    @Test
    void shouldNotReturnSession_whenSessionIdNotExists() {
        final var getSession = new GetSession(this.sessionRepository);
        assertThrows(NotFoundException.class, () -> getSession.execute(-1L));
    }

    @Test
    void shouldThrow_whenSessionRepositoryThrows() {
        final var mockRepository = mock(SessionRepositoryMemory.class);
        final var getSession = new GetSession(mockRepository);
        when(mockRepository.findBySessionId(any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> getSession.execute(1L));
    }
}
