package com.challenge.vote.application.usecases.vote;

import com.challenge.vote.application.errors.badrequest.SessionBadRequestException;
import com.challenge.vote.domain.entities.Vote;
import com.challenge.vote.domain.integrations.member.MemberIntegration;
import com.challenge.vote.domain.repositories.SessionRepository;
import com.challenge.vote.domain.repositories.VoteRepository;
import com.challenge.vote.domain.services.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;

@Component
public class DoVote {
    private final SessionRepository sessionRepository;
    private final VoteRepository voteRepository;
    private final MemberIntegration memberIntegration;

    @Autowired
    public DoVote(SessionRepository sessionRepository, VoteRepository voteRepository, MemberIntegration memberIntegration) {
        this.sessionRepository = sessionRepository;
        this.voteRepository = voteRepository;
        this.memberIntegration = memberIntegration;
    }

    public void execute(DoVoteInput input) {
        final var memberValidator = new MemberValidator(this.memberIntegration);
        if (!memberValidator.isAble(input.getCpf()))
            throw new SessionBadRequestException("Member is not able to vote.");
        final var session = this.sessionRepository.findBySessionId(input.getSessionId());
        if (!session.isOpen(now())) throw new SessionBadRequestException("Session is not open.");
        final var vote = this.voteRepository.findBySessionIdAndCpf(input.getSessionId(), input.getCpf());
        if (!isNull(vote)) throw new SessionBadRequestException("Vote already exists.");
        this.voteRepository.save(new Vote(input.getId(), input.getSessionId(), input.getCpf(), input.getInFavor(), input.getCreatedAt()));
    }
}
