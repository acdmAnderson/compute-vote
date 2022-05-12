package com.challenge.vote.application.usecases.session;

import com.challenge.vote.infra.repositories.memories.SessionRepositoryMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GetSessionTest {

    @Autowired
    private SessionRepositoryMemory sessionRepository;

    @BeforeEach
    void setup() {
        this.sessionRepository.clean();
    }

    @Test
    void shouldGetSession () throws Exception {
        final var input = CreateSessionInput.builder()
                .sessionId(1L)
                .description("ANY_SESSION")
                .duration(3600L)
                .build();
        final var createSession = new CreateSession(this.sessionRepository);
        createSession.execute(input);
        final var getSession = new GetSession(this.sessionRepository);
        final var output = getSession.execute(input.getSessionId());
        Assertions.assertEquals(output.getSessionId(), 1L);
        Assertions.assertEquals(output.getSessionDescription(), "ANY_SESSION");
        Assertions.assertEquals(output.getSessionDuration(), 3600L);
    }
}
