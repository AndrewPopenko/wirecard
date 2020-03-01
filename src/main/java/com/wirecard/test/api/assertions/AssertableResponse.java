package com.wirecard.test.api.assertions;


import com.wirecard.test.api.conditions.Condition;
import io.restassured.response.Response;

public class AssertableResponse {
    private Response response;
    public AssertableResponse(Response res) {
        response = res;
    }

    public AssertableResponse shouldHave(Condition condition) {
        condition.check(response);
        return this;
    }

    public <T> T asPojo(Class<T> tClass) {
        return response.as(tClass);
    }
}
