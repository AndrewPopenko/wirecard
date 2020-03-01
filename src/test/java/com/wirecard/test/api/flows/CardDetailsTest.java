package com.wirecard.test.api.flows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.wirecard.test.api.data.CardDetailsData;
import com.wirecard.test.api.services.JsonBody;
import com.wirecard.test.api.services.card.details.CardDetails;
import com.wirecard.test.api.services.payloads.user.UserPayloadRequest;
import com.wirecard.test.api.services.responses.user.RegisterUserResponse;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.wirecard.test.api.conditions.Conditions.bodyFieldCondition;
import static com.wirecard.test.api.conditions.Conditions.statusCode;
import static com.wirecard.test.api.data.CardDetailsData.wrongPan;
import static com.wirecard.test.api.data.JsonPath.*;
import static com.wirecard.test.api.services.responses.ErrorDescription.CARD_NOT_FOUND;
import static com.wirecard.test.api.services.responses.ErrorDescription.CARD_NOT_FOUND_DESCRIPTION;
import static org.hamcrest.CoreMatchers.is;

public class CardDetailsTest {
    @Rule
    public WireMockRule wm = new WireMockRule(options().port(3123));

    private CardDetailsData cardDetailsData;
    private UserPayloadRequest newUser;
    private String holderName;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:3123";
        cardDetailsData = new CardDetailsData();

        newUser = cardDetailsData.getNewValidUser();
        holderName = newUser.getFirstName() + " " + newUser.getLastName();
        String loginOfLockedUser = cardDetailsData.getRegisteredAndLockedUser().getLoginName();

        stubFor(get(urlPathMatching("\\/user\\/[a-z0-9]+\\/card\\/[0-9]{16}"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(cardDetailsData.getGreenPathActiveResponse()))))
                .setName("green path - ACTIVE");

        stubFor(get(urlPathMatching("\\/user\\/" + loginOfLockedUser + "\\/card\\/[0-9]{16}"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(cardDetailsData.getGreenPathLockedResponse()))))
                .setName("green path - LOCKED");

        stubFor(get(urlPathMatching("\\/user\\/[a-z0-9]+\\/card/" + wrongPan))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(cardDetailsData.getRedPathCardNotFound()))))
                .setName("red path - CARD NOT FOUND");
    }

    @Test
    public void greenPath() {
        RegisterUserResponse registeredUser = cardDetailsData.getRegisteredUser();
        new CardDetails().getCardDetails(registeredUser.getLoginName(), registeredUser.getPan())
                .shouldHave(statusCode(200))
                .shouldHave(bodyFieldCondition(card_number,
                        is(registeredUser.getPan())))
                .shouldHave(bodyFieldCondition(holder_name,
                        is(holderName)))
                .shouldHave(bodyFieldCondition(card_status,
                        is("ACTIVE")));
    }

    @Test
    public void greenPathLockedUser() {
        RegisterUserResponse lockedUser = cardDetailsData.getRegisteredAndLockedUser();
        new CardDetails().getCardDetails(lockedUser.getLoginName(), lockedUser.getPan())
                .shouldHave(statusCode(200))
                .shouldHave(bodyFieldCondition(holder_name,
                        is(holderName)))
                .shouldHave(bodyFieldCondition(card_status,
                        is("LOCKED")));
    }

    @Test
    public void redPathCardNotFOund() {
        new CardDetails().getCardDetails("johnsmith1980", wrongPan)
                .shouldHave(statusCode(404))
                .shouldHave(bodyFieldCondition(error_code,
                        is(404)))
                .shouldHave(bodyFieldCondition(error_key,
                        is(CARD_NOT_FOUND)))
                .shouldHave(bodyFieldCondition(error_message,
                        is(CARD_NOT_FOUND_DESCRIPTION)));
    }

    private String toJson(JsonBody response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        String resp = "";
        try {
            resp = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resp;
    }
}
