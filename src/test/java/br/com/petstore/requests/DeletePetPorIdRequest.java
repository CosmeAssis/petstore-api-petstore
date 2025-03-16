package br.com.petstore.requests;

import br.com.petstore.utils.RequestBase;

public class DeletePetPorIdRequest extends RequestBase {
    public DeletePetPorIdRequest(int petId) {
        this.service = "/pet/" + petId;
        this.method = "DELETE";
    }
}
