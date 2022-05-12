package com.challenge.vote.application.usecases.session;

import com.challenge.vote.infra.repositories.memories.SessionRepositoryMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        final var input = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createSession = new CreateSession(this.sessionRepository);
        createSession.execute(input);
        final var openSession = new OpenSession(this.sessionRepository);
        openSession.execute(input.getSessionId());
        final var session = this.sessionRepository.findById(input.getSessionId());
        Assertions.assertTrue(session.isOpen(LocalDateTime.now()));
    }
}
