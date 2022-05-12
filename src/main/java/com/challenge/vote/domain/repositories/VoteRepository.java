package com.challenge.vote.domain.repositories;

import com.challenge.vote.domain.entities.Vote;

import java.util.List;

public interface VoteRepository {
    void save(Vote vote);
    Vote findByIdSessionAndCpf(Long idSession, String cpf);
    List<Vote> findByIdSession(Long idSession);
    void clean();
}
