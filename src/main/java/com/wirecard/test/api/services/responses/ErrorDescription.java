package com.wirecard.test.api.services.responses;

public class ErrorDescription {
    public static final String MISSING_MANDATORY_FIELD = "MISSING_MANDATORY_FIELD";
    public static final String MISSING_PARAMETER = "MISSING_PARAMETER";
    public static final String USER_WITH_THIS_PHONE_NUMBER_EXISTS = "USER_WITH_THIS_PHONE_NUMBER_EXISTS";
    public static final String USER_WITH_THIS_LOGIN_NAME_EXISTS = "USER_WITH_THIS_LOGIN_NAME_EXISTS";
    public static final String USER_WITH_THIS_EMAIL_EXISTS = "USER_WITH_THIS_EMAIL_EXISTS";
    public static final String CARD_NOT_FOUND = "CARD_NOT_FOUND";
    public static final String TRANSACTION_ALREADY_USED = "TRANSACTION_ALREADY_USED";
    public static final String CARD_ALREADY_LOCKED = "CARD_ALREADY_LOCKED";
    public static final String NO_CARD_FOUND = "NO_CARD_FOUND";

    public static final String MISSING_MANDATORY_FIELD_DESCRIPTION = "One or several mandatory fields are missing";
    public static final String MISSING_PARAMETER_DESCRIPTION = "Bad Request, missing card PAN";
    public static final String USER_WITH_THIS_PHONE_NUMBER_EXISTS_DESCRIPTION = "User with this mobile phone number already exists";
    public static final String USER_WITH_THIS_LOGIN_NAME_EXISTS_DESCRIPTION = "User with this login name already exists";
    public static final String USER_WITH_THIS_EMAIL_EXISTS_DESCRIPTION = "User with this email address already exists";
    public static final String CARD_NOT_FOUND_DESCRIPTION = "Card not found";
    public static final String TRANSACTION_ALREADY_USED_DESCRIPTION = "Transaction with the same id already exists on the server.";
    public static final String CARD_ALREADY_LOCKED_DESCRIPTION = "Card is already in LOCKED state";
    public static final String NO_CARD_FOUND_DESCRIPTION = "No card found for the given PAN";
}
