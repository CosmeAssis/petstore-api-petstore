package br.com.petstore.utils;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public abstract class RequestBase {
    protected String baseUrl = PropertiesLoader.getProperty("base.url");
    protected String endpoint;
    protected String method;
    protected Object body;

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public ValidatableResponse executeRequest() {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .request(method, baseUrl + endpoint)
                .then();
    }
}
