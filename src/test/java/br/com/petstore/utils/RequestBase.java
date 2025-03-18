package br.com.petstore.utils;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public abstract class RequestBase {
    public String service;
    protected String method;
    protected Object jsonBody;

    public void setJsonBody(Object object) {
        this.jsonBody = object;
    }

    public ValidatableResponse executeRequest() {
        if (jsonBody != null) {
            return given()
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .when()
                    .request(method, PropertiesLoader.getProperty("base.url") + service)
                    .then();
        } else {
            return given()
                    .header("Content-Type", "application/json")
                    .when()
                    .request(method, PropertiesLoader.getProperty("base.url") + service)
                    .then();
        }
    }
}