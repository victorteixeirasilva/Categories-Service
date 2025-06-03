package tech.inovasoft.inevolving.ms.categories.api;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestAddObjectiveToCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.request.RequestUpdateCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseCategoryDTO;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.categories.service.client.dto.RequestCreateObjectiveDTO;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerSuccessTest {

    @LocalServerPort
    private int port;

    public static Faker faker = new Faker();

    private static UUID idUser = UUID.randomUUID();
    private static UUID idObjective;
    private static UUID idCategory;
    private static UUID oldIdCategory;


    @Test
    public void addCategory_Ok() {
        RequestCategoryDTO requestCategoryDTO = new RequestCategoryDTO(
                faker.name().firstName(),
                faker.name().lastName()
        );

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification.body(requestCategoryDTO)
                .when()
                .post("http://localhost:" + port + "/ms/categories/" + idUser)
                .then();

        idCategory = UUID.fromString(response.extract().jsonPath().get("id"));

        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("id", Matchers.notNullValue()).and()
                .body("idUser", equalTo(idUser.toString())).and()
                .body("categoryName", equalTo(requestCategoryDTO.categoryName())).and()
                .body("categoryDescription", equalTo(requestCategoryDTO.categoryDescription()));
    }

    @Test
    public void addObjectiveToCategory_Ok() {
        // Cria a especificação da requisição
        RequestSpecification requestSpecificationObjectives = given()
                .contentType(ContentType.JSON);

        RequestCreateObjectiveDTO requestCreateObjectiveDTO = new RequestCreateObjectiveDTO(
                faker.name().firstName(),
                faker.name().lastName(),
                idUser
        );

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse responseObjectives = requestSpecificationObjectives
                .body(requestCreateObjectiveDTO)
                .when()
                .post("http://localhost:8080/ms/objectives")
                .then();

        idObjective = UUID.fromString(responseObjectives.extract().jsonPath().get("id"));

        RequestAddObjectiveToCategoryDTO requestAddObjectiveToCategoryDTO =
                new RequestAddObjectiveToCategoryDTO(
                        idCategory,
                        idObjective
                );

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .body(requestAddObjectiveToCategoryDTO)
                .when()
                .post("http://localhost:" + port + "/ms/categories/objective/" + idUser)
                .then();

        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("message", equalTo("Objective add successfully")).and()
                .body("idCategory", equalTo(idCategory.toString())).and()
                .body("idUser", equalTo(idUser.toString()));

    }

    @Test
    public void updateCategory_Ok() {
        var requestUpdateCategoryDTO = new RequestUpdateCategoryDTO(
                faker.name().firstName(),
                faker.name().lastName()
        );

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .body(requestUpdateCategoryDTO)
                .when()
                .patch("http://localhost:" + port + "/ms/categories/" + idUser + "/" + idCategory)
                .then();

        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("id", equalTo(idCategory.toString())).and()
                .body("idUser", equalTo(idUser.toString())).and()
                .body("categoryName", equalTo(requestUpdateCategoryDTO.categoryName())).and()
                .body("categoryDescription", equalTo(requestUpdateCategoryDTO.categoryDescription()));
    }

    @Test
    public void getCategories_Ok() {

        // Cria um novo registro
        oldIdCategory = idCategory;
        addCategory_Ok();

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .when()
                .get("http://localhost:" + port + "/ms/categories/" + idUser)
                .then();

        // Valida a resposta
        List<ResponseCategoryDTO> caregories =
                response.extract().jsonPath().get("categories");

        assertEquals(2, caregories.size());

        response.assertThat().statusCode(200).and()
                .body("idUser", equalTo(idUser.toString()));
    }

    @Test
    public void removeCategory_Ok() {
        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .when()
                .delete("http://localhost:" + port + "/ms/categories/" + idUser + "/" + oldIdCategory)
                .then();

        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("message", equalTo("Category removed successfully"));

        // Cria a especificação da requisição
        RequestSpecification requestSpecificationGet = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse responseGet = requestSpecification
                .when()
                .get("http://localhost:" + port + "/ms/categories/" + idUser)
                .then();

        // Valida a resposta
        List<ResponseCategoryDTO> caregoriesGet =
                responseGet.extract().jsonPath().get("categories");

        assertEquals(1, caregoriesGet.size());
        //TODO: Desenvolver o teste
    }

    @Test
    public void getObjectivesByCategory_Ok() {
        //TODO: Desenvolver o teste
    }

    @Test
    public void removeObjectiveToCategory_Ok() {
        //TODO: Desenvolver o teste
    }
}
