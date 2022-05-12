package com.challenge.vote.domain.repositories;

import com.challenge.vote.domain.entities.Session;

import java.util.List;

public interface SessionRepository {
    void save(Session session);
    List<Session> findAll();
    void clean();
    Session findById(Long id);
}
