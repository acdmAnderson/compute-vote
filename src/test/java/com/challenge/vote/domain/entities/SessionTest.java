package com.challenge.vote.domain.entities;

import com.challenge.vote.VoteApplicationTests;
import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.parse;
import static org.junit.jupiter.api.Assertions.*;

public class SessionTest implements VoteApplicationTests {

    @Test
    void shouldCreateSession() {
        final var session = new Session(1L, "ANY_SESSION", 3600L);
        assertEquals(session.getId(), 1L);
        assertEquals(session.getDescription(), "ANY_SESSION");
        assertEquals(session.getDuration(), 3600L);
        assertFalse(session.isOpen(now()));
    }

    @Test
    void shouldCreateSessionWithDefaultDuration() {
        final var session = new Session(1L, "ANY_SESSION");
        assertEquals(session.getDuration(), 60L);
    }

    @Test
    void shouldOpenSession() {
        final var session = new Session(1L, "ANY_SESSION", 3600L);
        session.open(parse("2020-01-01T10:00:00"));
        final var isOpen = session.isOpen(parse("2020-01-01T10:30:00"));
        assertTrue(isOpen);
    }
}
