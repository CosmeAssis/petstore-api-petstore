package br.com.petstore.requests;

import io.restassured.response.ValidatableResponse;
import br.com.petstore.objects.PetObject;
import br.com.petstore.utils.RequestBase;

public class PostCreatePetRequest extends RequestBase {
    public PostCreatePetRequest(PetObject pet) {
        setEndpoint("/pet");
        setMethod("POST");
        setBody(pet);
    }
}
