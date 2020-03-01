package com.wirecard.test.api.data;

import com.wirecard.test.api.services.payloads.card.lock.CardLockRequest;
import com.wirecard.test.api.services.payloads.user.UserPayloadRequest;
import com.wirecard.test.api.services.responses.card.CardDetailsResponse;
import com.wirecard.test.api.services.responses.error.Error;
import com.wirecard.test.api.services.responses.error.ErrorResponse;
import com.wirecard.test.api.services.responses.user.RegisterUserResponse;

import static com.wirecard.test.api.services.responses.ErrorDescription.*;

public class CardDetailsData {

    public static final String wrongPan = "1234567890012345";

    public CardDetailsResponse getGreenPathActiveResponse() {
        return new CardDetailsResponse()
                .setHolderName("John Smith")
                .setStatus("ACTIVE")
                .setNumber("4123456789012345");
    }

    public CardDetailsResponse getGreenPathLockedResponse() {
        return new CardDetailsResponse()
                .setHolderName("John Smith")
                .setStatus("LOCKED")
                .setNumber("4123456789012345");
    }

    public RegisterUserResponse getRegisteredUser() {
        return new RegisterUserResponse()
                .setLoginName("johnsmith1980")
                .setPan("4123456789012345")
                .setStatus("ACTIVE");
    }

    public RegisterUserResponse getRegisteredAndLockedUser() {
        return new RegisterUserResponse()
                .setLoginName("lockeduser")
                .setPan("4444333322221111")
                .setStatus("LOCKED");
    }

    public ErrorResponse getRedPathCardNotFound() {
        return new ErrorResponse()
                .setErrorCode(404)
                .setError(new Error()
                        .setErrorKey(CARD_NOT_FOUND)
                        .setErrorMessage(CARD_NOT_FOUND_DESCRIPTION));
    }

    public ErrorResponse getRedPathMissingParameter() {
        return new ErrorResponse()
                .setErrorCode(400)
                .setError(new Error()
                        .setErrorKey(MISSING_PARAMETER)
                        .setErrorMessage(MISSING_PARAMETER_DESCRIPTION));
    }

    public ErrorResponse getRedPathTransactionAlreadyUsed() {
        return new ErrorResponse()
                .setErrorCode(403)
                .setError(new Error()
                        .setErrorKey(TRANSACTION_ALREADY_USED)
                        .setErrorMessage(TRANSACTION_ALREADY_USED_DESCRIPTION));
    }

    public ErrorResponse getRedPathCardAlreadyLocked() {
        return new ErrorResponse()
                .setErrorCode(403)
                .setError(new Error()
                        .setErrorKey(CARD_ALREADY_LOCKED)
                        .setErrorMessage(CARD_ALREADY_LOCKED_DESCRIPTION));
    }

    public ErrorResponse getRedPathNoCardFound() {
        return new ErrorResponse()
                .setErrorCode(404)
                .setError(new Error()
                        .setErrorKey(NO_CARD_FOUND)
                        .setErrorMessage(NO_CARD_FOUND_DESCRIPTION));
    }

    public UserPayloadRequest getNewValidUser() {
        return new UserPayloadRequest()
                .setSalutation("MR")
                .setFirstName("John")
                .setLastName("Smith")
                .setBirthDate("1992-09-24")
                .setEmail("abc@def.co.de")
                .setLoginName("johnsmith1980")
                .setPassword("Johnny_1992")
                .setMobileNumber("+491234567890");
    }

    public CardLockRequest getLostCardRequest() {
        return new CardLockRequest().setLockingReason("lost card");
    }
}
