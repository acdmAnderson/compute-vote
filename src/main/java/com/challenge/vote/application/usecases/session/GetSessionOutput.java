package com.challenge.vote.application.usecases.session;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = GetSessionOutput.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class GetSessionOutput {
    Long sessionId;
    String sessionDescription;
    Long sessionDuration;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {
    }
}
