package com.wirecard.test.api.conditions;

import org.hamcrest.Matcher;

public class Conditions {

    private Conditions() { }

    public static StatusCodeCondition statusCode(int statusCode) {
        return new StatusCodeCondition(statusCode);
    }

    public static BodyFieldCondition bodyFieldCondition(String jsonPath, Matcher matcher) {
        return new BodyFieldCondition(jsonPath, matcher);
    }
}
