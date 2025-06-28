package br.com.petstore.tests;

import br.com.petstore.objects.PetCategoryObject;
import br.com.petstore.objects.PetObject;
import br.com.petstore.objects.PetTagObject;
import br.com.petstore.requests.PostCreatePetPorIdRequest;
import br.com.petstore.requests.PutUpdatePetPorIdRequest;
import br.com.petstore.utils.TestBase;
import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;

public class PutUpdatePetPorIdTest extends TestBase {

    private static final Faker faker = new Faker();

    PetCategoryObject petCategoryObject = new PetCategoryObject();
    PetObject petObject = new PetObject();
    PetTagObject petTagObject = new PetTagObject();

    @Test(description = "Atualizar Dados do Pet com Sucesso", groups = "Principal")
    public void atualizarPetComSucesso() {
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

        PostCreatePetPorIdRequest postRequest = new PostCreatePetPorIdRequest();
        postRequest.setJsonBody(petObject);
        ValidatableResponse postResponse = postRequest.executeRequest();

        int petId = postResponse.extract().jsonPath().getInt("id");
        int statusCodeEsperado = HttpStatus.SC_OK;
        //endregion

        // Criar objeto atualizado
        PetObject petAtualizado = new PetObject();
        petAtualizado.setId(petId);
        petAtualizado.setName("doggie");
        petAtualizado.setStatus("sold");

        //region Act
        PutUpdatePetPorIdRequest putUpdatePetPorIdRequest = new PutUpdatePetPorIdRequest();
        putUpdatePetPorIdRequest.setJsonBody(petAtualizado);
        ValidatableResponse response = putUpdatePetPorIdRequest.executeRequest();
        //endregion

        setTestDetails(putUpdatePetPorIdRequest.service, putUpdatePetPorIdRequest.method, petAtualizado, response);

        //region Assert
        response.statusCode(statusCodeEsperado);
        response.body("status", equalTo(petAtualizado.getStatus()));
        //endregion
    }
}