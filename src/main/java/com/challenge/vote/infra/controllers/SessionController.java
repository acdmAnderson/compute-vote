package com.challenge.vote.infra.controllers;

import com.challenge.vote.application.usecases.session.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/session")
public class SessionController {

    @Autowired
    private CreateSession createSession;

    @Autowired
    private GetSession getSession;

    @PostMapping
    @ResponseStatus(CREATED)
    public CreateSessionOutput create(@RequestBody final CreateSessionInput input) {
        return this.createSession.execute(input);
    }

    @GetMapping("/{sessionId}")
    @ResponseStatus(OK)
    public GetSessionOutput getSessionById(@PathVariable("sessionId") Long sessionId) throws Exception {
        return this.getSession.execute(sessionId);
    }
}
