package com.wirecard.test.api.flows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.wirecard.test.api.data.CardDetailsData;
import com.wirecard.test.api.services.JsonBody;
import com.wirecard.test.api.services.card.lock.CardLock;
import com.wirecard.test.api.services.payloads.card.lock.CardLockRequest;
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
import static com.wirecard.test.api.services.responses.ErrorDescription.*;
import static org.hamcrest.CoreMatchers.is;

public class CardLockTest {
    @Rule
    public WireMockRule wm = new WireMockRule(options().port(3123));

    private CardDetailsData cardDetailsData;
    private UserPayloadRequest newUser;
    private String transactionAlreadyUsed = "1d4d345f-1030-46c0-9ea7-bb629de";
    private CardLockRequest reason;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:3123";
        cardDetailsData = new CardDetailsData();

        reason = cardDetailsData.getLostCardRequest();

        newUser = cardDetailsData.getNewValidUser();
        String panOfLockedCard = cardDetailsData.getRegisteredAndLockedUser().getPan();

        stubFor(put(urlPathMatching("\\/user\\/[a-z0-9]+\\/card\\/[0-9]{16}\\/lock\\/[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{8}"))
                .withRequestBody(equalToJson(toJson(cardDetailsData.getLostCardRequest())))
                .willReturn(aResponse()
                        .withStatus(200)))
                .setName("green path - Card is locked");

        stubFor(put(urlPathMatching("\\/user\\/[a-z0-9]+\\/card"))
                .withRequestBody(equalToJson(toJson(cardDetailsData.getLostCardRequest())))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(cardDetailsData.getRedPathMissingParameter()))))
                .setName("red path - missing parameter");

        stubFor(put(urlPathMatching("\\/user\\/[a-z0-9]+\\/card\\/[0-9]{16}\\/lock\\/"+ transactionAlreadyUsed))
                .withRequestBody(equalToJson(toJson(cardDetailsData.getLostCardRequest())))
                .willReturn(aResponse()
                        .withStatus(403)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(cardDetailsData.getRedPathTransactionAlreadyUsed()))))
                .setName("red path - transaction already used");

        stubFor(put(urlPathMatching("\\/user\\/[a-z0-9]+\\/card\\/" + panOfLockedCard + "\\/lock\\/[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{8}"))
                .withRequestBody(equalToJson(toJson(cardDetailsData.getLostCardRequest())))
                .willReturn(aResponse()
                        .withStatus(403)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(cardDetailsData.getRedPathCardAlreadyLocked()))))
                .setName("red path - card already locked");

        stubFor(put(urlPathMatching("\\/user\\/[a-z0-9]+\\/card\\/" + wrongPan + "\\/lock\\/[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{8}"))
                .withRequestBody(equalToJson(toJson(cardDetailsData.getLostCardRequest())))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(cardDetailsData.getRedPathNoCardFound()))))
                .setName("red path - no card found");
    }

    @Test
    public void greenPath() {
        RegisterUserResponse registeredUser = cardDetailsData.getRegisteredUser();
        CardLockRequest reason = cardDetailsData.getLostCardRequest();

        new CardLock().lockCard(registeredUser.getLoginName(), registeredUser.getPan(), toJson(reason), "")
                .shouldHave(statusCode(200));
    }

    @Test
    public void redPathMissingParameter() {
        RegisterUserResponse registeredUser = cardDetailsData.getRegisteredUser();
        CardLockRequest reason = cardDetailsData.getLostCardRequest();

        new CardLock().lockCard(registeredUser.getLoginName(), "", toJson(reason), "")
                .shouldHave(statusCode(400))
                .shouldHave(bodyFieldCondition(error_code,
                        is(400)))
                .shouldHave(bodyFieldCondition(error_key,
                        is(MISSING_PARAMETER)))
                .shouldHave(bodyFieldCondition(error_message,
                        is(MISSING_PARAMETER_DESCRIPTION)));
    }

    @Test
    public void redPathTransactionAlreadyUsed() {
        RegisterUserResponse registeredUser = cardDetailsData.getRegisteredUser();
        CardLockRequest reason = cardDetailsData.getLostCardRequest();

        new CardLock().lockCard(registeredUser.getLoginName(), registeredUser.getPan(), toJson(reason), transactionAlreadyUsed)
                .shouldHave(statusCode(403))
                .shouldHave(bodyFieldCondition(error_code,
                        is(403)))
                .shouldHave(bodyFieldCondition(error_key,
                        is(TRANSACTION_ALREADY_USED)))
                .shouldHave(bodyFieldCondition(error_message,
                        is(TRANSACTION_ALREADY_USED_DESCRIPTION)));
    }

    @Test
    public void redPathCardAlreadyLocked() {
        RegisterUserResponse registeredUser = cardDetailsData.getRegisteredAndLockedUser();

        new CardLock().lockCard(registeredUser.getLoginName(), registeredUser.getPan(), toJson(reason), "")
                .shouldHave(statusCode(403))
                .shouldHave(bodyFieldCondition(error_code,
                        is(403)))
                .shouldHave(bodyFieldCondition(error_key,
                        is(CARD_ALREADY_LOCKED)))
                .shouldHave(bodyFieldCondition(error_message,
                        is(CARD_ALREADY_LOCKED_DESCRIPTION)));
    }

    @Test
    public void redPathNoCardFound() {
        RegisterUserResponse registeredUser = cardDetailsData.getRegisteredUser();

        new CardLock().lockCard(registeredUser.getLoginName(), wrongPan, toJson(reason), "")
                .shouldHave(statusCode(404))
                .shouldHave(bodyFieldCondition(error_code,
                        is(404)))
                .shouldHave(bodyFieldCondition(error_key,
                        is(NO_CARD_FOUND)))
                .shouldHave(bodyFieldCondition(error_message,
                        is(NO_CARD_FOUND_DESCRIPTION)));
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
