package com.challenge.vote.infra.controllers;

import com.challenge.vote.application.usecases.session.*;
import com.challenge.vote.infra.docs.SessionOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/session")
public class SessionController implements SessionOpenApi {

    @Autowired
    private CreateSession createSession;

    @Autowired
    private GetSession getSession;

    @Autowired
    private OpenSession openSession;

    @PostMapping
    @ResponseStatus(CREATED)
    @Override
    public CreateSessionOutput create(@RequestBody final CreateSessionInput input) {
        return this.createSession.execute(input);
    }

    @GetMapping("/{sessionId}")
    @ResponseStatus(OK)
    @Override
    public GetSessionOutput getSessionById(@PathVariable("sessionId") Long sessionId) {
        return this.getSession.execute(sessionId);
    }

    @PostMapping("/open/{sessionId}")
    @ResponseStatus(OK)
    @Override
    public void open(@PathVariable("sessionId") Long sessionId) {
        this.openSession.execute(sessionId);
    }
}
