package br.com.petstore.tests;

import br.com.petstore.objects.PetCategoryObject;
import br.com.petstore.objects.PetObject;
import br.com.petstore.objects.PetTagObject;
import br.com.petstore.requests.DeletePetPorIdRequest;
import br.com.petstore.requests.PostCreatePetPorIdRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static java.util.Collections.singletonList;

public class DeletePetPorIdTest extends TestBase {

    PetCategoryObject petCategoryObject = new PetCategoryObject();
    PetObject petObject = new PetObject();
    PetTagObject petTagObject = new PetTagObject();

    @Test(priority = 1, groups = "Principal")
    public void deletarPetComSucesso() {
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

        PostCreatePetPorIdRequest postCreatePetPorIdRequest = new PostCreatePetPorIdRequest();
        postCreatePetPorIdRequest.setJsonBody(petObject);
        ValidatableResponse postResponse = postCreatePetPorIdRequest.executeRequest();

        int petId = postResponse.extract().jsonPath().getInt("id");
        //endregion

        //region Act
        DeletePetPorIdRequest deletePetPorIdRequest = new DeletePetPorIdRequest(petId);
        ValidatableResponse response = deletePetPorIdRequest.executeRequest();
        //endregion

        setTestDetails(deletePetPorIdRequest.service, null, response);

        //region Assert
        response.statusCode(HttpStatus.SC_OK);
        //endregion
    }

    @Test(priority = 2, groups = "Exceção")
    public void deletarPetComIdInvalido() {
        //region Arrange
        int petId = 999999;
        //endregion

        //region Act
        DeletePetPorIdRequest deletePetPorIdRequest = new DeletePetPorIdRequest(petId);
        ValidatableResponse response = deletePetPorIdRequest.executeRequest();
        //endregion

        setTestDetails(deletePetPorIdRequest.service, null, response);

        //region Assert
        response.statusCode(HttpStatus.SC_NOT_FOUND);
        //endregion
    }
}