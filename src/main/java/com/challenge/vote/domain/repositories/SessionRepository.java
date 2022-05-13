package com.challenge.vote.domain.repositories;

import com.challenge.vote.domain.entities.Session;

import java.util.List;

public interface SessionRepository {
    Session save(Session session);
    List<Session> findAll();
    void clean();
    Session findBySessionId(Long id);
}
