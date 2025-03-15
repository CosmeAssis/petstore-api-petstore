package br.com.petstore.tests;

import br.com.petstore.requests.GetPetRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class GetPetTest extends TestBase {

    @Test(priority = 1, groups = "Principal")
    public void consultarPetComSucesso() {
        //region Arrange
        int petId = 1001;
        int statusCodeEsperado = HttpStatus.SC_OK;
        //endregion

        //region Act
        GetPetRequest getPetRequest = new GetPetRequest(petId);
        ValidatableResponse response = getPetRequest.executeRequest();
        //endregion

        //region Assert
        response.statusCode(statusCodeEsperado);
        response.body("id", equalTo(petId));
        //endregion
    }
}
