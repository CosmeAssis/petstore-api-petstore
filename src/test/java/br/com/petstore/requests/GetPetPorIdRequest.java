package br.com.petstore.requests;

import br.com.petstore.utils.RequestBase;

public class GetPetPorIdRequest extends RequestBase {
    public GetPetPorIdRequest(int petId) {
        this.service = "/pet/" + petId;
        this.method = "GET";
    }
}
