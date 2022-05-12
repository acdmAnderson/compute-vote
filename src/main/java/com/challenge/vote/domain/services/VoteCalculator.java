package com.challenge.vote.domain.services;

import com.challenge.vote.domain.entities.Vote;

import java.util.List;

public class VoteCalculator {
    private final List<Vote> votes;

    public VoteCalculator(List<Vote> votes) {
        this.votes = votes;
    }

    public Long getTotal() {
        return Integer.toUnsignedLong(this.votes.size());
    }

    public Long getInFavor() {
        return this.votes.stream()
                .filter(vote -> Boolean.TRUE.equals(vote.getInFavor()))
                .count();
    }

    public Long getNotInFavor() {
        return this.votes.stream()
                .filter(vote -> Boolean.FALSE.equals(vote.getInFavor()))
                .count();
    }
}
