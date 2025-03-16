package br.com.petstore.tests;

import br.com.petstore.objects.PetObject;
import br.com.petstore.requests.PostCreatePetRequest;
import br.com.petstore.utils.TestBase;
import io.restassured.response.ValidatableResponse;
import lombok.Getter;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import java.util.Collections;

public class PostCreatePetTest extends TestBase {
    @Getter
    private static PetObject petCadastrado;

    @Test(priority = 1, groups = "Principal")
    public void cadastrarNovoPetComSucesso() {
        // Arrange
        PetObject pet = new PetObject();
        pet.setId(1002);

        PetObject.Category category = new PetObject.Category();
        category.setId(1002);
        category.setName("Bidu");
        pet.setCategory(category);

        pet.setName("doggie");
        pet.setPhotoUrls(Collections.singletonList("string"));

        PetObject.Tag tag = new PetObject.Tag();
        tag.setId(0);
        tag.setName("Dog");
        pet.setTags(Collections.singletonList(tag));

        pet.setStatus("available");

        // Act
        PostCreatePetRequest request = new PostCreatePetRequest();
        request.setJsonBody(pet);
        ValidatableResponse response = request.executeRequest();

        // Salva o objeto inteiro para os outros testes
        petCadastrado = response.extract().as(PetObject.class);

        // Configurar detalhes para o relatório
        setTestDetails(request.service, pet, response);

        // Assert - Validando todos os campos da resposta
        response.statusCode(HttpStatus.SC_OK);
        response.body("id", equalTo(pet.getId()));
        response.body("category.id", equalTo(pet.getCategory().getId()));
        response.body("category.name", equalTo(pet.getCategory().getName()));
        response.body("name", equalTo(pet.getName()));
        response.body("photoUrls[0]", equalTo(pet.getPhotoUrls().get(0)));
        response.body("tags[0].id", equalTo(pet.getTags().get(0).getId()));
        response.body("tags[0].name", equalTo(pet.getTags().get(0).getName()));
        response.body("status", equalTo(pet.getStatus()));
    }
}