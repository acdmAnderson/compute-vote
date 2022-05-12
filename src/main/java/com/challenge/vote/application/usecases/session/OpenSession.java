package com.challenge.vote.application.usecases.session;

import com.challenge.vote.domain.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class OpenSession {

    private final SessionRepository sessionRepository;

    @Autowired
    public OpenSession(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void execute(Long sessionId) throws Exception {
        final var session = this.sessionRepository.findById(sessionId);
        if (Objects.isNull(session)) throw new Exception("Session not found");
        session.open(LocalDateTime.now());
        this.sessionRepository.save(session);
    }
}
