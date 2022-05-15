package com.challenge.vote.application.usecases.session;

import com.challenge.vote.domain.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;

@Component
public class OpenSession {

    private final SessionRepository sessionRepository;

    @Autowired
    public OpenSession(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void execute(Long sessionId) throws Exception {
        final var session = this.sessionRepository.findBySessionId(sessionId);
        if (isNull(session)) throw new Exception("Session not found");
        if (!isNull(session.getStartDate())) throw new Exception("Session is already open");
        session.open(now());
        this.sessionRepository.save(session);
    }
}
