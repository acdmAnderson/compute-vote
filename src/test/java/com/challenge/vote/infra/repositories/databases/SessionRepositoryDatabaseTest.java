package com.challenge.vote.infra.repositories.databases;

import com.challenge.vote.domain.entities.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SessionRepositoryDatabaseTest {

    @Autowired
    private SessionRepositoryDatabase sessionRepositoryDatabase;

    @BeforeEach
    void setup () {
        this.sessionRepositoryDatabase.clean();
    }

    @Test
    public void shouldSaveSession() {
        final var session = new Session(null, "ANY_SESSION");
        final var savedSession = this.sessionRepositoryDatabase.save(session);
        Assertions.assertEquals(savedSession.getId(), 1L);
        Assertions.assertEquals(savedSession.getDescription(), "ANY_SESSION");
        Assertions.assertEquals(savedSession.getDuration(), 60L);
    }

    @Test
    public void shouldGetSessions() {
        final var session = new Session(null, "ANY_SESSION");
        this.sessionRepositoryDatabase.save(session);
        final var sessions = this.sessionRepositoryDatabase.findAll();
        Assertions.assertEquals(sessions.size(), 1);
        Assertions.assertEquals(sessions.get(0).getDescription(), "ANY_SESSION");
        Assertions.assertEquals(sessions.get(0).getDuration(), 60L);
    }
}
