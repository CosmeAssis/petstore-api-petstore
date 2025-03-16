package br.com.petstore.requests;

import br.com.petstore.utils.RequestBase;

public class PostCreatePetRequest extends RequestBase {
    public PostCreatePetRequest() {
        this.service = "/pet";
        this.method = "POST";
    }
}
