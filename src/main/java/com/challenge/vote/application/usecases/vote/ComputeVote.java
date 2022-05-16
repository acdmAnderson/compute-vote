package com.challenge.vote.application.usecases.vote;

import com.challenge.vote.domain.repositories.SessionRepository;
import com.challenge.vote.domain.repositories.VoteRepository;
import com.challenge.vote.domain.services.VoteCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComputeVote {
    private final VoteRepository voteRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public ComputeVote(VoteRepository voteRepository, SessionRepository sessionRepository) {
        this.voteRepository = voteRepository;
        this.sessionRepository = sessionRepository;
    }

    public ComputeVoteOutput execute(Long sessionId) throws Exception {
        final var session = this.sessionRepository.findBySessionId(sessionId);
        final var votes = this.voteRepository.findBySessionId(sessionId);
        final var voteCalculator = new VoteCalculator(votes);
        return ComputeVoteOutput.builder()
                .session(session.getDescription())
                .inFavorQuantity(voteCalculator.getInFavor())
                .notInFavorQuantity(voteCalculator.getNotInFavor())
                .total(voteCalculator.getTotal())
                .build();
    }
}
