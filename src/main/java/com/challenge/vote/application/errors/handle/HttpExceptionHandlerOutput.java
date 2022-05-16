package com.challenge.vote.application.errors.handle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import static java.lang.System.currentTimeMillis;

@Value
@JsonDeserialize(builder = HttpExceptionHandlerOutput.JacksonBuilder.class)
@Builder(builderClassName = "JacksonBuilder")
public class HttpExceptionHandlerOutput {

    String path;

    String message;

    Long timestamp = currentTimeMillis();

    Integer code;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JacksonBuilder {
    }
}
