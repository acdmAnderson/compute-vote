package com.challenge.vote;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static java.time.LocalDateTime.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SessionTest {

    @Test
    void shouldCreateSession() {
        final var session = new Session(1L, "ANY_SESSION", 3600L, parse("2020-01-01T10:00:00"));
        assertEquals(session.getId(), 1L);
        assertEquals(session.getDescription(), "ANY_SESSION");
        assertEquals(session.getDuration(), 3600L);
        assertEquals(session.getStartDate(), parse("2020-01-01T10:00:00"));
    }
}
