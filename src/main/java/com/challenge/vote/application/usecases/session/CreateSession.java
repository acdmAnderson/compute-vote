package com.challenge.vote.application.usecases.session;

import com.challenge.vote.domain.entities.Session;
import com.challenge.vote.domain.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateSession {

    private final SessionRepository sessionRepository;

    @Autowired
    public CreateSession(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void execute(CreateSessionInput input) {
        final var session = new Session(input.getSessionId(), input.getDescription(), input.getDuration());
        this.sessionRepository.save(session);
    }
}
