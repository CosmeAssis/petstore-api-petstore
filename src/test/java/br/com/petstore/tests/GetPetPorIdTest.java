package br.com.petstore.tests;

import br.com.petstore.objects.PetObject;
import br.com.petstore.requests.GetPetPorIdRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class GetPetPorIdTest extends TestBase {

    @Test(priority = 1, groups = "Principal", dependsOnMethods = "br.com.petstore.tests.PostCreatePetTest.cadastrarNovoPetComSucesso")
    public void consultarPetComSucesso() {
        // Arrange
        PetObject petEsperado = PostCreatePetTest.getPetCadastrado();
        int petId = petEsperado.getId();
        int statusCodeEsperado = HttpStatus.SC_OK;

        // Act
        GetPetPorIdRequest getPetPorIdRequest = new GetPetPorIdRequest(petId);
        ValidatableResponse response = getPetPorIdRequest.executeRequest();

        // Configurar detalhes para o relatório
        setTestDetails(getPetPorIdRequest.service, null, response);

        // Assert - Validando todos os campos da resposta
        response.statusCode(statusCodeEsperado);
        response.body("id", equalTo(petEsperado.getId()));
        response.body("category.id", equalTo(petEsperado.getCategory().getId()));
        response.body("category.name", equalTo(petEsperado.getCategory().getName()));
        response.body("name", equalTo(petEsperado.getName()));
        response.body("photoUrls[0]", equalTo(petEsperado.getPhotoUrls().get(0)));
        response.body("tags[0].id", equalTo(petEsperado.getTags().get(0).getId()));
        response.body("tags[0].name", equalTo(petEsperado.getTags().get(0).getName()));
        response.body("status", equalTo(petEsperado.getStatus()));
    }
}