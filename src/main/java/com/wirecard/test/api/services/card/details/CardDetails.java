package com.wirecard.test.api.services.card.details;


import com.wirecard.test.api.assertions.AssertableResponse;
import com.wirecard.test.api.services.ApiService;

public class CardDetails extends ApiService {
    public AssertableResponse getCardDetails(String loginName, String pan) {
        String url = "/user/" + loginName + "/card/" + pan;
        return new AssertableResponse(setup()
                .when()
                .get(url));
    }
}
