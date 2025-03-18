package br.com.petstore.requests;

import br.com.petstore.utils.RequestBase;

public class PostCreatePetPorIdRequest extends RequestBase {
    public PostCreatePetPorIdRequest() {
        this.service = "/pet";
        this.method = "POST";
    }
}
