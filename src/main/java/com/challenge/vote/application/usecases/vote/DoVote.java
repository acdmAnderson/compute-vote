package com.challenge.vote.application.usecases.vote;

import com.challenge.vote.domain.entities.Vote;
import com.challenge.vote.domain.repositories.SessionRepository;
import com.challenge.vote.domain.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

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
        final var session = this.sessionRepository.findBySessionId(input.getIdSession());
        if (Objects.isNull(session)) throw new Exception("Session not found");
        if (!session.isOpen(LocalDateTime.now())) throw new Exception("Session is not Open");
        final var vote = this.voteRepository.findByIdSessionAndCpf(input.getIdSession(), input.getCpf());
        if (!Objects.isNull(vote)) throw new Exception("Vote already exists");
        this.voteRepository.save(new Vote(input.getId(), input.getIdSession(), input.getCpf(), input.getInFavor(), input.getCreatedAt()));
    }
}
