package com.challenge.vote.infra.repositories.databases;

import com.challenge.vote.domain.entities.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SessionRepositoryDatabaseTest {

    @Autowired
    private SessionRepositoryDatabase sessionRepositoryDatabase;

    @Test
   public void shouldSaveSession() {
        final var session = new Session(null, "ANY_SESSION");
        final var savedSession = this.sessionRepositoryDatabase.save(session);
        Assertions.assertEquals(savedSession.getId(), 1L);
        Assertions.assertEquals(savedSession.getDescription(), "ANY_SESSION");
        Assertions.assertEquals(savedSession.getDuration(), 60L);
    }
}
