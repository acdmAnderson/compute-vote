package com.challenge.vote.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class SessionControllerTestConstants {
    public static final String SESSION_URL = "/v1/session";
    public static final String JSON_PATH_SESSION_ID = "$.sessionId";
    public static final String JSON_PATH_SESSION_DESCRIPTION = "$.sessionDescription";
    public static final String JSON_PATH_SESSION_DURATION = "$.sessionDuration";
}
