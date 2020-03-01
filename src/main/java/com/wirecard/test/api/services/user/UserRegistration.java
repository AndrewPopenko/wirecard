package com.wirecard.test.api.services.user;


import com.wirecard.test.api.assertions.AssertableResponse;
import com.wirecard.test.api.services.ApiService;

public class UserRegistration extends ApiService {
    public AssertableResponse register(String json) {
        return new AssertableResponse(setup()
                .body(json)
                .when()
                .put("/user"));
    }
}
