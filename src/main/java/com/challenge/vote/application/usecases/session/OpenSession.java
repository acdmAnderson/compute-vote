package com.challenge.vote.application.usecases.session;

import com.challenge.vote.domain.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

@Component
public class OpenSession {

    private final SessionRepository sessionRepository;

    @Autowired
    public OpenSession(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void execute(Long sessionId) {
        final var session = this.sessionRepository.findBySessionId(sessionId);
        session.open(now());
        this.sessionRepository.save(session);
    }
}
