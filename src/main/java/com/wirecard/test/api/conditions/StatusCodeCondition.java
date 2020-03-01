package com.wirecard.test.api.conditions;

import io.restassured.response.Response;

public class StatusCodeCondition implements Condition {

    private final int statusCode;

    public StatusCodeCondition(int statusCode) {
        this.statusCode = statusCode;
    }

    public void check(Response response) {
        response.then()/*.log().all()*/.assertThat().statusCode(statusCode);
    }
}
