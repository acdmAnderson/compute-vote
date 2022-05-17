package com.challenge.vote.infra.controllers;

import com.challenge.vote.application.usecases.vote.ComputeVote;
import com.challenge.vote.application.usecases.vote.ComputeVoteOutput;
import com.challenge.vote.application.usecases.vote.DoVote;
import com.challenge.vote.application.usecases.vote.DoVoteInput;
import com.challenge.vote.infra.docs.VoteOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/vote")
public class VoteController implements VoteOpenApi {

    @Autowired
    private DoVote doVote;

    @Autowired
    private ComputeVote computeVote;

    @PostMapping
    @ResponseStatus(CREATED)
    @Override
    public void create(@RequestBody final DoVoteInput input) {
        this.doVote.execute(input);
    }

    @GetMapping("/compute/{sessionId}")
    @ResponseStatus(OK)
    @Override
    public ComputeVoteOutput compute(@PathVariable("sessionId") Long sessionId) {
        return this.computeVote.execute(sessionId);
    }
}
