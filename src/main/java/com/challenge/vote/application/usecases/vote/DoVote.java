package com.challenge.vote.application.usecases.vote;

import com.challenge.vote.domain.entities.Vote;
import com.challenge.vote.domain.repositories.SessionRepository;
import com.challenge.vote.domain.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;

@Component
public class DoVote {
    private final SessionRepository sessionRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public DoVote(SessionRepository sessionRepository, VoteRepository voteRepository) {
        this.sessionRepository = sessionRepository;
        this.voteRepository = voteRepository;
    }

    public void execute(DoVoteInput input) throws Exception {
        final var session = this.sessionRepository.findBySessionId(input.getSessionId());
        if (isNull(session)) throw new Exception("Session not found");
        if (!session.isOpen(now())) throw new Exception("Session is not Open");
        final var vote = this.voteRepository.findBySessionIdAndCpf(input.getSessionId(), input.getCpf());
        if (!isNull(vote)) throw new Exception("Vote already exists");
        this.voteRepository.save(new Vote(input.getId(), input.getSessionId(), input.getCpf(), input.getInFavor(), input.getCreatedAt()));
    }
}
