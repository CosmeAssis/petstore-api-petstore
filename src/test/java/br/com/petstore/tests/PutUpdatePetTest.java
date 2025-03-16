package br.com.petstore.tests;

import br.com.petstore.objects.PetObject;
import br.com.petstore.requests.PutUpdatePetRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class PutUpdatePetTest extends TestBase {
    @Test(priority = 1, groups = "Principal", dependsOnMethods = "br.com.petstore.tests.PostCreatePetTest.cadastrarNovoPetComSucesso")
    public void atualizarPetComSucesso() {
        // Arrange
        PetObject pet = new PetObject();
        pet.setId(PostCreatePetTest.getPetCadastrado().getId());
        pet.setName("doggie");
        pet.setStatus("sold");

        // Act
        PutUpdatePetRequest request = new PutUpdatePetRequest();
        request.setJsonBody(pet);
        ValidatableResponse response = request.executeRequest();

        // Configurar detalhes para o relatório
        setTestDetails(request.service, pet, response);

        // Assert
        response.statusCode(HttpStatus.SC_OK);
        response.body("status", equalTo("sold"));
    }
}