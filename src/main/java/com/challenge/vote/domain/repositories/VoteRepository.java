package com.challenge.vote.domain.repositories;

import com.challenge.vote.domain.entities.Vote;

import java.util.List;

public interface VoteRepository {
    Vote save(Vote vote);
    Vote findBySessionIdAndCpf(Long sessionId, String cpf);
    List<Vote> findBySessionId(Long sessionId);
    void clean();
}
