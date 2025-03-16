package br.com.petstore.tests;

import br.com.petstore.requests.DeletePetPorIdRequest;
import br.com.petstore.requests.GetPetPorIdRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class DeletePetPorIdTest extends TestBase {
    @Test(priority = 1, groups = "Principal", dependsOnMethods = "br.com.petstore.tests.PutUpdatePetTest.atualizarPetComSucesso")
    public void deletarPetPorIdComSucesso() {
        // Arrange
        int petId = PostCreatePetTest.getPetCadastrado().getId();

        // Act
        DeletePetPorIdRequest deletePetPorIdRequest = new DeletePetPorIdRequest(petId);
        ValidatableResponse response = deletePetPorIdRequest.executeRequest();

        // Configurar detalhes para o relatório
        setTestDetails(deletePetPorIdRequest.service, null, response);

        // Assert
        response.statusCode(HttpStatus.SC_OK);
    }
}