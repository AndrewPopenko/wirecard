package com.wirecard.test.api.services;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApiService {
    public RequestSpecification setup() {
        return RestAssured
                .given().contentType(ContentType.JSON)//.log().all()
                .filters(getFilters());
    }

    private List<Filter> getFilters() {
        boolean loggingEnabled = Boolean.parseBoolean(System.getProperty("logging", "true"));

        if (loggingEnabled) {
            return Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter());
        }
        return Collections.emptyList();
    }
}
