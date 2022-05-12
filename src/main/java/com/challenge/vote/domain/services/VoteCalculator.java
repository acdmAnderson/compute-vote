package com.challenge.vote.domain.services;

import com.challenge.vote.domain.entities.Vote;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Integer.toUnsignedLong;

public class VoteCalculator {
    private final List<Vote> votes;

    public VoteCalculator(List<Vote> votes) {
        this.votes = votes;
    }

    public Long getTotal() {
        return toUnsignedLong(this.votes.size());
    }

    public Long getInFavor() {
        return this.getVoteByOption(TRUE);
    }

    public Long getNotInFavor() {
        return this.getVoteByOption(FALSE);
    }

    private Long getVoteByOption(Boolean option) {
        return this.votes.stream()
                .filter(vote -> option.equals(vote.getInFavor()))
                .count();
    }
}
