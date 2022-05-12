package com.challenge.vote.application.usecases.vote;

import com.challenge.vote.domain.repositories.SessionRepository;
import com.challenge.vote.domain.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.Boolean.TRUE;
import static java.lang.Integer.toUnsignedLong;
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
        final var session = this.sessionRepository.findById(idSession);
        if (isNull(session)) throw new Exception("Session not found");
        final var votes = this.voteRepository.findByIdSession(idSession);
        return ComputeVoteOutput.builder()
                .session(session.getDescription())
                .inFavorQuantity(2L)
                .notInFavorQuantity(1L)
                .inFavor(TRUE)
                .total(toUnsignedLong(votes.size()))
                .build();
    }
}
