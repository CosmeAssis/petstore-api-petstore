package br.com.petstore.requests;

import br.com.petstore.utils.RequestBase;

public class PutUpdatePetPorIdRequest extends RequestBase {
    public PutUpdatePetPorIdRequest() {
        this.service = "/pet";
        this.method = "PUT";
    }
}