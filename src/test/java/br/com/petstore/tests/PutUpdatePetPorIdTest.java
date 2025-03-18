package br.com.petstore.tests;

import br.com.petstore.objects.PetCategoryObject;
import br.com.petstore.objects.PetObject;
import br.com.petstore.objects.PetTagObject;
import br.com.petstore.requests.PostCreatePetPorIdRequest;
import br.com.petstore.requests.PutUpdatePetPorIdRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;

public class PutUpdatePetPorIdTest extends TestBase {

    PetCategoryObject petCategoryObject = new PetCategoryObject();
    PetObject petObject = new PetObject();
    PetTagObject petTagObject = new PetTagObject();

    @Test(priority = 1, groups = "Principal")
    public void atualizarPetComSucesso() {
        //region Arrange
        petCategoryObject.setId(152031);
        petCategoryObject.setName("Cachorro");

        petTagObject.setId(100);
        petTagObject.setName("Dog");

        petObject.setId(1003);
        petObject.setCategory(petCategoryObject);
        petObject.setName("doggie");
        petObject.setPhotoUrls(singletonList("String"));
        petObject.setTags(singletonList(petTagObject));
        petObject.setStatus("available");

        PostCreatePetPorIdRequest postRequest = new PostCreatePetPorIdRequest();
        postRequest.setJsonBody(petObject);
        ValidatableResponse postResponse = postRequest.executeRequest();

        int petId = postResponse.extract().jsonPath().getInt("id");
        int statusCodeEsperado =  HttpStatus.SC_OK;
        //endregion

        // Criar objeto atualizado
        PetObject petAtualizado = new PetObject();
        petAtualizado.setId(petId);
        petAtualizado.setName("doggie");
        petAtualizado.setStatus("sold");

        //region Act
        PutUpdatePetPorIdRequest putRequest = new PutUpdatePetPorIdRequest();
        putRequest.setJsonBody(petAtualizado);
        ValidatableResponse response = putRequest.executeRequest();
        //endregion

        setTestDetails(putRequest.service, petAtualizado, response);

        //region Assert
        response.statusCode(statusCodeEsperado);
        response.body("status", equalTo(petAtualizado.getStatus()));
        //endregion
    }
}
