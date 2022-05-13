package com.challenge.vote.infra.repositories.databases;

import com.challenge.vote.domain.entities.Session;
import com.challenge.vote.domain.repositories.SessionRepository;
import com.challenge.vote.infra.repositories.adapters.SessionJPAAdapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class SessionRepositoryDatabase implements SessionRepository {

    private final SessionJPAAdapterRepository repository;

    @Autowired
    public SessionRepositoryDatabase(SessionJPAAdapterRepository repository) {
        this.repository = repository;
    }

    @Override
    public Session save(Session session) {
        return this.repository.save(session);
    }

    @Override
    public List<Session> findAll() {
        return this.repository.findAll();
    }

    @Override
    public void clean() {
        this.repository.deleteAll();
    }

    @Override
    public Session findBySessionId(Long id) {
        return this.repository.findById(id)
                .orElse(null);
    }
}
