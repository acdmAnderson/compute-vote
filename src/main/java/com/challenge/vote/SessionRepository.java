package com.challenge.vote;

import java.util.List;

public interface SessionRepository {
    void save(Session session);
    List<Session> findAll();
    void clean();
}
