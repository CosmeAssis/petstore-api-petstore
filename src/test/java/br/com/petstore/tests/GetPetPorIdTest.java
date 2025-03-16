package br.com.petstore.tests;

import br.com.petstore.requests.GetPetPorIdRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

public class GetPetPorIdTest extends TestBase {
    @Test(priority = 1, groups = "Principal")
    public void consultarPetComSucesso() {
        //region Arrange
        int petId = 1001;
        int statusCodeEsperado = HttpStatus.SC_OK;
        //endregion

        //region Act
        GetPetPorIdRequest getPetPorIdRequest = new GetPetPorIdRequest(petId);
        ValidatableResponse response = getPetPorIdRequest.executeRequest();
        //endregion

        //region Configurar detalhes para o relatório
        setTestDetails(getPetPorIdRequest.service, null, response);
        //endregion

        //region Assert
        response.statusCode(statusCodeEsperado);
        response.body("id", equalTo(petId));
        //endregion
    }
}