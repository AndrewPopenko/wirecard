package com.wirecard.test.api.services.card.lock;

import com.wirecard.test.api.assertions.AssertableResponse;
import com.wirecard.test.api.services.ApiService;

import java.util.UUID;

public class CardLock extends ApiService {
    public AssertableResponse lockCard(String loginName, String pan, String body, String id) {
        String uuid = id.isEmpty() ? UUID.randomUUID().toString().substring(0, 32) : id;
        String url = pan.isEmpty() ? "/user/" + loginName + "/card" : "/user/" + loginName + "/card/" + pan + "/lock/" + uuid;
        return new AssertableResponse(setup()
                .body(body)
                .when()
                .put(url));
    }
}
