package br.com.petstore.tests;

import br.com.petstore.requests.DeletePetPorIdRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

public class DeletePetPorIdTest extends TestBase {

    @Test(priority = 1, groups = "Principal", dependsOnMethods = "br.com.petstore.tests.PutUpdatePetTest.atualizarPetComSucesso")
    public void deletarPetPorIdComSucesso() {
        //region Arrange
        int petId = PostCreatePetTest.getPetCadastrado().getId();
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
}