package com.wirecard.test.api.flows;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.wirecard.test.api.data.UserData;
import com.wirecard.test.api.services.JsonBody;
import com.wirecard.test.api.services.payloads.user.UserPayloadRequest;
import com.wirecard.test.api.services.user.UserRegistration;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.wirecard.test.api.conditions.Conditions.bodyFieldCondition;
import static com.wirecard.test.api.conditions.Conditions.statusCode;
import static com.wirecard.test.api.data.JsonPath.*;
import static com.wirecard.test.api.services.responses.ErrorDescription.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class UserApiTest {
    @Rule
    public WireMockRule wm = new WireMockRule(options().port(3123));

    private UserData userData;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:3123";
        userData = new UserData();

        stubFor(put(urlEqualTo("/user"))
                .withRequestBody(equalToJson(toJson(userData.getValidUser())))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(userData.getGreenPathResponse()))))
                .setName("green path");

        stubFor(put(urlEqualTo("/user"))
                .withRequestBody(equalToJson(toJson(userData.getInvalidUser())))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(userData.getRedPathMissingMandatoryFieldResponse()))))
                .setName("red path missing mandatory field");

        stubFor(put(urlEqualTo("/user"))
                .withRequestBody(equalToJson(toJson(userData.getUserWithSamePhoneNumber())))
                .willReturn(aResponse()
                        .withStatus(403)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(userData.getRedPathUserWithThisPhoneNumberExists()))))
                .setName("phone number exists");

        stubFor(put(urlEqualTo("/user"))
                .withRequestBody(equalToJson(toJson(userData.getUserWithSameLoginName())))
                .willReturn(aResponse()
                        .withStatus(403)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(userData.getRedPathUserWithThisLoginNameExists()))))
                .setName("login name exists");

        stubFor(put(urlEqualTo("/user"))
                .withRequestBody(equalToJson(toJson(userData.getUserWithSameEmail())))
                .willReturn(aResponse()
                        .withStatus(403)
                        .withHeader("Content-Type", "application/json")
                        .withBody(toJson(userData.getRedPathUserWithThisEmailExists()))))
                .setName("email exists");
    }

    @Test
    public void greenPath() {
        UserPayloadRequest user = userData.getValidUser();

        new UserRegistration().register(toJson(user))
                .shouldHave(statusCode(200))
                .shouldHave(bodyFieldCondition(login_name,
                        is(user.getLoginName())))
                .shouldHave(bodyFieldCondition(user_status,
                        is("ACTIVE")))
                .shouldHave(bodyFieldCondition(user_pan,
                        not(isEmptyOrNullString())));
    }

    @Test
    public void redPathMissingMandatoryField() {
        new UserRegistration().register(toJson(userData.getInvalidUser()))
                .shouldHave(statusCode(400))
                .shouldHave(bodyFieldCondition(error_code, is(400)))
                .shouldHave(bodyFieldCondition(error_key,
                        is(MISSING_MANDATORY_FIELD)))
                .shouldHave(bodyFieldCondition(error_message,
                        is(MISSING_MANDATORY_FIELD_DESCRIPTION)));
    }

    @Test
    public void redPathUserWithSamePhoneNumberExists() {
        new UserRegistration().register(toJson(userData.getUserWithSamePhoneNumber()))
                .shouldHave(statusCode(403))
                .shouldHave(bodyFieldCondition(error_code,
                        is(403)))
                .shouldHave(bodyFieldCondition(error_key,
                        is(USER_WITH_THIS_PHONE_NUMBER_EXISTS)))
                .shouldHave(bodyFieldCondition(error_message,
                        is(USER_WITH_THIS_PHONE_NUMBER_EXISTS_DESCRIPTION)));
    }

    @Test
    public void redPathUserWithSameLoginName() {
        new UserRegistration().register(toJson(userData.getUserWithSameLoginName()))
                .shouldHave(statusCode(403))
                .shouldHave(bodyFieldCondition(error_code,
                        is(403)))
                .shouldHave(bodyFieldCondition(error_key,
                        is(USER_WITH_THIS_LOGIN_NAME_EXISTS)))
                .shouldHave(bodyFieldCondition(error_message,
                        is(USER_WITH_THIS_LOGIN_NAME_EXISTS_DESCRIPTION)));
    }

    @Test
    public void redPathUserWithSameEmail() {
        new UserRegistration().register(toJson(userData.getUserWithSameEmail()))
                .shouldHave(statusCode(403))
                .shouldHave(bodyFieldCondition(error_code,
                        is(403)))
                .shouldHave(bodyFieldCondition(error_key,
                        is(USER_WITH_THIS_EMAIL_EXISTS)))
                .shouldHave(bodyFieldCondition(error_message,
                        is(USER_WITH_THIS_EMAIL_EXISTS_DESCRIPTION)));
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