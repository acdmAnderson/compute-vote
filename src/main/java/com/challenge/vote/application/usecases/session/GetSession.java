package com.challenge.vote.application.usecases.session;

import com.challenge.vote.domain.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GetSession {
    private final SessionRepository sessionRepository;

    @Autowired
    public GetSession(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public GetSessionOutput execute(Long sessionId) throws Exception {
        final var session = this.sessionRepository.findById(sessionId);
        if (Objects.isNull(session)) throw new Exception("Session not found");
        return  GetSessionOutput.builder()
                .sessionId(session.getId())
                .sessionDescription(session.getDescription())
                .sessionDuration(session.getDuration())
                .build();
    }

}
