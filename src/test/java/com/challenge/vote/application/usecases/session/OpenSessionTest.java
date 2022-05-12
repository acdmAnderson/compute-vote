package com.challenge.vote.application.usecases.session;

import com.challenge.vote.infra.repositories.memories.SessionRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OpenSessionTest {

    @Autowired
    private SessionRepositoryMemory sessionRepository;

    @BeforeEach
    void setup() {
        this.sessionRepository.clean();
    }

    @Test
    void shouldOpenSession() throws Exception {
        final var createSession = new CreateSession(this.sessionRepository);
        final var openSession = new OpenSession(this.sessionRepository);
        final var input = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        createSession.execute(input);
        openSession.execute(input.getSessionId());
        final var session = this.sessionRepository.findById(input.getSessionId());
        assertTrue(session.isOpen(now()));
    }
}
