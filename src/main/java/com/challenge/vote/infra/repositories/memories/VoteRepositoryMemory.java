package com.challenge.vote.infra.repositories.memories;

import com.challenge.vote.domain.entities.Vote;
import com.challenge.vote.domain.repositories.VoteRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class VoteRepositoryMemory implements VoteRepository {
    private final List<Vote> votes;

    public VoteRepositoryMemory() {
        this.votes = new ArrayList<>();
    }

    @Override
    public void save(Vote vote) {
        this.votes.add(vote);
    }

    @Override
    public Vote findByIdSessionAndCpf(Long idSession, String cpf) {
        return this.votes.stream()
                .filter(vote -> vote.getIdSession().equals(idSession) && vote.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Vote> findByIdSession(Long idSession) {
        return this.votes.stream()
                .filter(vote -> vote.getIdSession().equals(idSession))
                .collect(toList());
    }

    @Override
    public void clean() {
        this.votes.clear();
    }
}
