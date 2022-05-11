package com.challenge.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class SessionTest {

    @Test
    void shouldCreateSession() {
        final var session = new Session(1L, "ANY_SESSION", 3600L, LocalDateTime.parse("2020-01-01T10:00:00"));
        Assertions.assertEquals(session.getId(), 1L);
        Assertions.assertEquals(session.getDescription(), "ANY_SESSION");
        Assertions.assertEquals(session.getDuration(), 3600L);
        Assertions.assertEquals(session.getStartDate(), LocalDateTime.parse("2020-01-01T10:00:00"));
    }
}
