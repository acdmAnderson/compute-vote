package com.challenge.vote.application.errors.badrequest;

public class SessionBadRequestException extends BadRequestException {
    public SessionBadRequestException(String message) {
        super(message);
    }
}
