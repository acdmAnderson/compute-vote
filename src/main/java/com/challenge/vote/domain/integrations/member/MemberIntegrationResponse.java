package com.challenge.vote.domain.integrations.member;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = MemberIntegrationResponse.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class MemberIntegrationResponse {

    MemberStatusEnum status;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {
    }
}
