package br.com.petstore.requests;

import io.restassured.response.ValidatableResponse;
import br.com.petstore.utils.RequestBase;

public class GetPetRequest extends RequestBase {
    public GetPetRequest(int petId) {
        setMethod("GET");
        setEndpoint("/pet/" + petId);
    }
}