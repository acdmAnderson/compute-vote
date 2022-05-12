package com.challenge.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateSessionTest {

    @Autowired
    private SessionRepositoryMemory sessionRepository;

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
}
