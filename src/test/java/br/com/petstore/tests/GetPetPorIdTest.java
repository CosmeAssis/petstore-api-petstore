package br.com.petstore.tests;

import br.com.petstore.objects.PetCategoryObject;
import br.com.petstore.objects.PetObject;
import br.com.petstore.objects.PetTagObject;
import br.com.petstore.requests.GetPetPorIdRequest;
import br.com.petstore.requests.PostCreatePetPorIdRequest;
import br.com.petstore.utils.TestBase;
import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;

public class GetPetPorIdTest extends TestBase {

    private static final Faker faker = new Faker();

    PetCategoryObject petCategoryObject = new PetCategoryObject();
    PetObject petObject = new PetObject();
    PetTagObject petTagObject = new PetTagObject();

    @Test(description = "Consulta de Pet por ID Com Sucesso", groups = "Principal")
    public void consultarPetPorIdComSucesso() {
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

        int petIdEsperado = postResponse.extract().jsonPath().getInt("id");
        int statusEsperado = HttpStatus.SC_OK;

        GetPetPorIdRequest getPetPorIdRequest = new GetPetPorIdRequest(petIdEsperado);
        ValidatableResponse response = getPetPorIdRequest.executeRequest();

        setTestDetails(getPetPorIdRequest.service, getPetPorIdRequest.method, null, response);

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
    }

    @Test(description = "Consultar Pet por ID Inexistente", groups = "Exceção")
    public void consultarPetPorIdInexistente() {
        int petIdEsperado = 199999300;
        int statusEsperado = HttpStatus.SC_NOT_FOUND;
        String mensagemEsperada = "Pet not found";

        GetPetPorIdRequest getPetPorIdRequest = new GetPetPorIdRequest(petIdEsperado);
        ValidatableResponse response = getPetPorIdRequest.executeRequest();

        setTestDetails(getPetPorIdRequest.service, getPetPorIdRequest.method, null, response);

        response.statusCode(statusEsperado);
        response.body("message", equalTo(mensagemEsperada));
    }
}