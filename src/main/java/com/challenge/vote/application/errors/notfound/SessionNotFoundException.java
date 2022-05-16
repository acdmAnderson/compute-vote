package com.challenge.vote.application.errors.notfound;

public class SessionNotFoundException extends NotFoundException {
    public SessionNotFoundException() {
        super("Session not found.");
    }
}
