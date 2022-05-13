package com.challenge.vote.application.usecases.vote;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@JsonDeserialize(builder = DoVoteInput.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class DoVoteInput {
    Long id;
    Long sessionId;
    String cpf;
    LocalDateTime createdAt = LocalDateTime.now();
    Boolean inFavor;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {
    }
}
