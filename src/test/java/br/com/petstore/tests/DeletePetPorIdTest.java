package br.com.petstore.tests;

import br.com.petstore.objects.PetCategoryObject;
import br.com.petstore.objects.PetObject;
import br.com.petstore.objects.PetTagObject;
import br.com.petstore.requests.DeletePetPorIdRequest;
import br.com.petstore.requests.PostCreatePetPorIdRequest;
import br.com.petstore.utils.TestBase;
import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static java.util.Collections.singletonList;

public class DeletePetPorIdTest extends TestBase {

    private static final Faker faker = new Faker();

    PetCategoryObject petCategoryObject = new PetCategoryObject();
    PetObject petObject = new PetObject();
    PetTagObject petTagObject = new PetTagObject();

    @Test(description = "Deletar Pet com Sucesso", groups = "Principal")
    public void deletarPetComSucesso() {
        //region Arrange
        petCategoryObject.setId(faker.number().numberBetween(1000, 9999));
        petCategoryObject.setName(faker.animal().name());

        petTagObject.setId(faker.number().randomDigit());
        petTagObject.setName(faker.color().name());

        petObject.setId(faker.number().numberBetween(1000, 9999));
        petObject.setCategory(petCategoryObject);
        petObject.setName(faker.name().firstName());
        petObject.setPhotoUrls(singletonList(faker.internet().avatar()));
        petObject.setTags(singletonList(petTagObject));
        petObject.setStatus("available");

        PostCreatePetPorIdRequest postCreatePetPorIdRequest = new PostCreatePetPorIdRequest();
        postCreatePetPorIdRequest.setJsonBody(petObject);
        ValidatableResponse postResponse = postCreatePetPorIdRequest.executeRequest();

        int petId = postResponse.extract().jsonPath().getInt("id");
        //endregion

        //region Acct
        DeletePetPorIdRequest deletePetPorIdRequest = new DeletePetPorIdRequest(petId);
        ValidatableResponse response = deletePetPorIdRequest.executeRequest();

        setTestDetails(deletePetPorIdRequest.service, deletePetPorIdRequest.method, null, response);
        //endregion

        //region Assert
        response.statusCode(HttpStatus.SC_OK);
        //endregion
    }

    @Test(description = "Deletar Pet com Id Inexistente na base", groups = "Exceção")
    public void deletarPetComIdInexistente() {
        //region Arrange
        int petId = 999837618;
        //endregion

        //region Act
        DeletePetPorIdRequest deletePetPorIdRequest = new DeletePetPorIdRequest(petId);
        ValidatableResponse response = deletePetPorIdRequest.executeRequest();
        //endregion

        setTestDetails(deletePetPorIdRequest.service, deletePetPorIdRequest.method, null, response);

        //region Assert
        response.statusCode(HttpStatus.SC_NOT_FOUND);
        //endregion
    }
}