package br.com.petstore.utils;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

public class RestAssuredUtils {

    public static void validateStatusCode(ValidatableResponse response, int expectedStatusCode) {
        response.statusCode(expectedStatusCode);
    }

    public static void validateField(ValidatableResponse response, String field, Object expectedValue) {
        response.body(field, Matchers.equalTo(expectedValue));
    }
}
