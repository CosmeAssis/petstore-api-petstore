package br.com.petstore.requests;

import br.com.petstore.utils.RequestBase;

public class PutUpdatePetRequest extends RequestBase {
    public PutUpdatePetRequest() {
        this.service = "/pet";
        this.method = "PUT";
    }
}