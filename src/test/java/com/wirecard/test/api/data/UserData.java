package com.wirecard.test.api.data;

import com.wirecard.test.api.services.payloads.user.UserPayloadRequest;
import com.wirecard.test.api.services.responses.ErrorDescription;
import com.wirecard.test.api.services.responses.error.Error;
import com.wirecard.test.api.services.responses.error.ErrorResponse;
import com.wirecard.test.api.services.responses.user.RegisterUserResponse;

public class UserData {

    public RegisterUserResponse getGreenPathResponse() {
        return new RegisterUserResponse()
                .setLoginName("johnsmith1980")
                .setPan("4123456789012345")
                .setStatus("ACTIVE");
    }

    public ErrorResponse getRedPathMissingMandatoryFieldResponse() {
        return new ErrorResponse()
                .setErrorCode(400)
                .setError(new Error()
                        .setErrorKey(ErrorDescription.MISSING_MANDATORY_FIELD)
                        .setErrorMessage(ErrorDescription.MISSING_MANDATORY_FIELD_DESCRIPTION)
                );
    }

    public ErrorResponse getRedPathUserWithThisPhoneNumberExists() {
        return new ErrorResponse()
                .setErrorCode(403)
                .setError(new Error()
                        .setErrorKey(ErrorDescription.USER_WITH_THIS_PHONE_NUMBER_EXISTS)
                        .setErrorMessage(ErrorDescription.USER_WITH_THIS_PHONE_NUMBER_EXISTS_DESCRIPTION)
                );
    }

    public ErrorResponse getRedPathUserWithThisLoginNameExists() {
        return new ErrorResponse()
                .setErrorCode(403)
                .setError(new Error()
                        .setErrorKey(ErrorDescription.USER_WITH_THIS_LOGIN_NAME_EXISTS)
                        .setErrorMessage(ErrorDescription.USER_WITH_THIS_LOGIN_NAME_EXISTS_DESCRIPTION)
                );
    }
    public ErrorResponse getRedPathUserWithThisEmailExists() {
        return new ErrorResponse()
                .setErrorCode(403)
                .setError(new Error()
                        .setErrorKey(ErrorDescription.USER_WITH_THIS_EMAIL_EXISTS)
                        .setErrorMessage(ErrorDescription.USER_WITH_THIS_EMAIL_EXISTS_DESCRIPTION)
                );
    }

    public UserPayloadRequest getValidUser() {
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

    public UserPayloadRequest getInvalidUser() {
        return new UserPayloadRequest()
                .setEmail("abc@def.co.de")
                .setLoginName("johnsmith1980");
    }

    public UserPayloadRequest getUserWithSamePhoneNumber() {
        return new UserPayloadRequest()
                .setMobileNumber("+491234555555");
    }

    public UserPayloadRequest getUserWithSameLoginName() {
        return new UserPayloadRequest()
                .setLoginName("existsLoginName");
    }

    public UserPayloadRequest getUserWithSameEmail() {
        return new UserPayloadRequest()
                .setEmail("existsEmail.co.de");
    }
}
