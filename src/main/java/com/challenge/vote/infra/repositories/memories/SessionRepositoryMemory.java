package com.challenge.vote.infra.repositories.memories;

import com.challenge.vote.domain.entities.Session;
import com.challenge.vote.domain.repositories.SessionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SessionRepositoryMemory implements SessionRepository {

    private final List<Session> sessions;

    SessionRepositoryMemory() {
        this.sessions = new ArrayList<>();
    }

    @Override
    public void save(Session session) {
        this.sessions.add(session);
    }

    @Override
    public List<Session> findAll() {
        return this.sessions;
    }

    @Override
    public void clean() {
        this.sessions.clear();
    }

    @Override
    public Session findById(Long id) {
        return this.sessions.stream()
                .filter(session -> session.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
