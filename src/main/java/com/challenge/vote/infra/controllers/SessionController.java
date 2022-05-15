package com.challenge.vote.infra.controllers;

import com.challenge.vote.application.usecases.session.CreateSession;
import com.challenge.vote.application.usecases.session.CreateSessionInput;
import com.challenge.vote.application.usecases.session.CreateSessionOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/session")
public class SessionController {

    @Autowired
    private CreateSession createSession;

    @PostMapping
    @ResponseStatus(CREATED)
    public CreateSessionOutput create(@RequestBody final CreateSessionInput input) {
        return this.createSession.execute(input);
    }
}
