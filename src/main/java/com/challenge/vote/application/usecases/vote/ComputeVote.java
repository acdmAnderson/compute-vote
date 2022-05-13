package com.challenge.vote.application.usecases.vote;

import com.challenge.vote.domain.repositories.SessionRepository;
import com.challenge.vote.domain.repositories.VoteRepository;
import com.challenge.vote.domain.services.VoteCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ComputeVote {
    private final VoteRepository voteRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public ComputeVote(VoteRepository voteRepository, SessionRepository sessionRepository) {
        this.voteRepository = voteRepository;
        this.sessionRepository = sessionRepository;
    }

    public ComputeVoteOutput execute(Long idSession) throws Exception {
        final var session = this.sessionRepository.findBySessionId(idSession);
        if (isNull(session)) throw new Exception("Session not found");
        final var votes = this.voteRepository.findByIdSession(idSession);
        final var voteCalculator = new VoteCalculator(votes);
        return ComputeVoteOutput.builder()
                .session(session.getDescription())
                .inFavorQuantity(voteCalculator.getInFavor())
                .notInFavorQuantity(voteCalculator.getNotInFavor())
                .total(voteCalculator.getTotal())
                .build();
    }
}
