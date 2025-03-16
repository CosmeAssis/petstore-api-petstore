package br.com.petstore.tests;

import br.com.petstore.objects.PetObject;
import br.com.petstore.requests.PostCreatePetRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

public class PostCreatePetTest extends TestBase {
    @Test(priority = 1, groups = "Principal")
    public void cadastrarNovoPetComSucesso() {
        // Arrange
        PetObject pet = new PetObject();
        pet.setId(1001);
        pet.setName("Bobby");
        pet.setStatus("available");

        // Act
        PostCreatePetRequest request = new PostCreatePetRequest();
        request.setJsonBody(pet);
        ValidatableResponse response = request.executeRequest();

        // Configurar detalhes para o relatório
        setTestDetails(request.service, pet, response);

        // Assert
        response.statusCode(HttpStatus.SC_OK);
        response.body("name", equalTo("Bobby"));
    }
}