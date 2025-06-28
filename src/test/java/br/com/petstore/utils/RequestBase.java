package br.com.petstore.utils;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.Setter;

import static io.restassured.RestAssured.given;

public abstract class RequestBase {
    public String service;
    public String method;

    @Setter
    protected Object jsonBody;

    public ValidatableResponse executeRequest() {
        RequestSpecification request = given()
                .header("Content-Type", "application/json");

        String token = PropertiesLoader.getProperty("access.token");
        if (token != null && !token.isEmpty()) {
            request.header("Authorization", "Bearer " + token);
        }

        if (jsonBody != null) {
            request.body(jsonBody);
        }

        return request
                .when()
                .request(method, PropertiesLoader.getProperty("base.url") + service)
                .then();
    }
}