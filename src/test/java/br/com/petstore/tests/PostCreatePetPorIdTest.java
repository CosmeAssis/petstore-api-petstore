package br.com.petstore.tests;

import br.com.petstore.objects.PetCategoryObject;
import br.com.petstore.objects.PetObject;
import br.com.petstore.objects.PetTagObject;
import br.com.petstore.requests.PostCreatePetPorIdRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;

public class PostCreatePetPorIdTest extends TestBase {

    PetCategoryObject petCategoryObject = new PetCategoryObject();
    PetObject petObject = new PetObject();
    PetTagObject petTagObject = new PetTagObject();

    @Test(priority = 1, groups = "Principal")
    public void cadastrarNovoPetComSucesso() {
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

        int statusCodeEsperado = HttpStatus.SC_OK;
        //endregion

        //region Act
        PostCreatePetPorIdRequest postCreatePetPorIdRequest = new PostCreatePetPorIdRequest();
        postCreatePetPorIdRequest.setJsonBody(petObject);
        ValidatableResponse response = postCreatePetPorIdRequest.executeRequest();
        //endregion

        setTestDetails(postCreatePetPorIdRequest.service, petObject, response);

        //region Assert
        response.statusCode(statusCodeEsperado);
        response.body("id", equalTo(petObject.getId()));
        response.body("category.id", equalTo(petObject.getCategory().getId()));
        response.body("category.name", equalTo(petObject.getCategory().getName()));
        response.body("name", equalTo(petObject.getName()));
        response.body("photoUrls[0]", equalTo(petObject.getPhotoUrls().get(0)));
        response.body("tags[0].id", equalTo(petObject.getTags().get(0).getId()));
        response.body("tags[0].name", equalTo(petObject.getTags().get(0).getName()));
        response.body("status", equalTo(petObject.getStatus()));
        //endregion
    }
}