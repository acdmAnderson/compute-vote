package com.challenge.vote.infra.controllers;

import com.challenge.vote.application.usecases.vote.ComputeVote;
import com.challenge.vote.application.usecases.vote.ComputeVoteOutput;
import com.challenge.vote.application.usecases.vote.DoVote;
import com.challenge.vote.application.usecases.vote.DoVoteInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/vote")
public class VoteController {

    @Autowired
    private DoVote doVote;

    @Autowired
    private ComputeVote computeVote;

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(@RequestBody final DoVoteInput input) throws Exception {
        this.doVote.execute(input);
    }

    @GetMapping("/compute/{sessionId}")
    @ResponseStatus(OK)
    public ComputeVoteOutput compute(@PathVariable("sessionId") Long sessionId) throws Exception {
        return this.computeVote.execute(sessionId);
    }
}
