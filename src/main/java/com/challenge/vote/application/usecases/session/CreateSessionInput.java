package com.challenge.vote.application.usecases.session;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = CreateSessionInput.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class CreateSessionInput {

    String description;
    Long duration;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {
    }
}
