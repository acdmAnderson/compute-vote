package com.challenge.vote.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class VoteControllerTestConstants {
    public static final String VOTE_URL = "/v1/vote";
    public static final String JSON_PATH_SESSION = "$.session";
    public static final String JSON_PATH_IN_FAVOR_QUANTITY = "$.inFavorQuantity";
    public static final String JSON_PATH_NOT_IN_FAVOR_QUANTITY = "$.notInFavorQuantity";
    public static final String JSON_PATH_TOTAL = "$.total";
}
