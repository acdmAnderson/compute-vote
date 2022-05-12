package com.challenge.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateSession {

    private final SessionRepository sessionRepository;

    @Autowired
    CreateSession(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    void execute(CreateSessionInput input) {
        final var session = new Session(null, input.getDescription(), input.getDuration());
        this.sessionRepository.save(session);
    }
}
