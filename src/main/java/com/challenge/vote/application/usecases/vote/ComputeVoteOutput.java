package com.challenge.vote.application.usecases.vote;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = ComputeVoteOutput.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class ComputeVoteOutput {
    String session;
    Long inFavorQuantity;
    Long notInFavorQuantity;
    Long total;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {
    }
}
