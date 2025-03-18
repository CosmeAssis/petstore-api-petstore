package br.com.petstore.tests;

import br.com.petstore.objects.PetCategoryObject;
import br.com.petstore.objects.PetObject;
import br.com.petstore.objects.PetTagObject;
import br.com.petstore.requests.GetPetPorIdRequest;
import br.com.petstore.requests.PostCreatePetPorIdRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;

public class GetPetPorIdTest extends TestBase {

    PetCategoryObject petCategoryObject = new PetCategoryObject();
    PetObject petObject = new PetObject();
    PetTagObject petTagObject = new PetTagObject();

    @Test(priority = 1, groups = "Principal")
    public void consultarPetPorIdComSucesso() {
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

        int petIdEsperado = postResponse.extract().jsonPath().getInt("id");
        int statusEsperado = HttpStatus.SC_OK;
        //endregion

        //region Act
        GetPetPorIdRequest getPetPorIdRequest = new GetPetPorIdRequest(petIdEsperado);
        ValidatableResponse response = getPetPorIdRequest.executeRequest();
        //endregion

        setTestDetails(getPetPorIdRequest.service, null, response);

        //region Assert
        response.statusCode(statusEsperado);
        response.body("id", equalTo(petObject.getId()));
        response.body("name", equalTo(petObject.getName()));
        response.body("status", equalTo(petObject.getStatus()));
        response.body("category.id", equalTo(petCategoryObject.getId()));
        response.body("category.name", equalTo(petCategoryObject.getName()));
        response.body("photoUrls[0]", equalTo(petObject.getPhotoUrls().get(0)));
        response.body("photoUrls.size()", equalTo(petObject.getPhotoUrls().size()));
        response.body("tags[0].id", equalTo(petTagObject.getId()));
        response.body("tags[0].name", equalTo(petTagObject.getName()));
        response.body("tags.size()", equalTo(petObject.getTags().size()));
        //endregion
    }

    @Test(priority = 2, groups = "Exceção")
    public void consultarPetPorIdInexistente() {
        //region Arrange
        int petIdEsperado = 100300;

        int statusEsperado = HttpStatus.SC_NOT_FOUND;
        String mensagemEsperada = "Pet not found";
        //endregion

        // Act
        GetPetPorIdRequest getPetPorIdRequest = new GetPetPorIdRequest(petIdEsperado);
        ValidatableResponse response = getPetPorIdRequest.executeRequest();
        //endregion

        setTestDetails(getPetPorIdRequest.service, null, response);

        //region Assert
        response.statusCode(statusEsperado);
        response.body("message", equalTo(mensagemEsperada));
        //endregion
    }
}