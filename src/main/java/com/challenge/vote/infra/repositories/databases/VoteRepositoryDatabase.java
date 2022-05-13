package com.challenge.vote.infra.repositories.databases;

import com.challenge.vote.domain.entities.Vote;
import com.challenge.vote.domain.repositories.VoteRepository;
import com.challenge.vote.infra.repositories.adapters.VoteJpaAdapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public class VoteRepositoryDatabase implements VoteRepository {

    private final VoteJpaAdapterRepository repository;

    @Autowired
    public VoteRepositoryDatabase(VoteJpaAdapterRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vote save(Vote vote) {
        return this.repository.save(vote);
    }

    @Override
    public Vote findBySessionIdAndCpf(Long sessionId, String cpf) {
        return this.repository.findBySessionIdAndCpf(sessionId, cpf);
    }

    @Override
    public List<Vote> findBySessionId(Long sessionId) {
        return this.repository.findBySessionId(sessionId);
    }

    @Override
    public void clean() {
        this.repository.deleteAll();
    }
}
