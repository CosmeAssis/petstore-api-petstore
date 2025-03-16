package br.com.petstore.requests;

import br.com.petstore.utils.RequestBase;

public class GetPetRequest extends RequestBase {
    public GetPetRequest(int petId) {
        this.service = "/pet/" + petId;
        this.method = "GET";
    }
}
