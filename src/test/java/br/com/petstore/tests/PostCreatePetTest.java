package br.com.petstore.tests;

import br.com.petstore.objects.PetObject;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;
import br.com.petstore.requests.PostCreatePetRequest;
import io.restassured.response.ValidatableResponse;
import br.com.petstore.utils.TestBase;

import static org.hamcrest.Matchers.equalTo;

public class PostCreatePetTest extends TestBase {

    @Test(priority = 1, groups = "Principal")
    public void cadastrarNovoPetComSucesso() {
        //region Acct
        PetObject pet = new PetObject();
        pet.setId(1001);
        pet.setName("Bobby");
        pet.setStatus("available");
        //endregion

        //region Acct
        PostCreatePetRequest request = new PostCreatePetRequest(pet);
        ValidatableResponse response = request.executeRequest();
        //endregion

        //region Assert
        response.statusCode(HttpStatus.SC_OK);
        response.body("name", equalTo("Bobby"));
        //endregion
    }
}
