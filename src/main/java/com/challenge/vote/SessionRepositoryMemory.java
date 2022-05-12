package com.challenge.vote;

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
}
