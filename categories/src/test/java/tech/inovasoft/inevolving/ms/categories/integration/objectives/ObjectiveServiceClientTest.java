package tech.inovasoft.inevolving.ms.categories.integration.objectives;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import tech.inovasoft.inevolving.ms.categories.domain.dto.response.ResponseObjectiveDTO;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ObjectiveServiceClientTest {


    @Test
    public void integrationTest_Ok() {
        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification.when()
                .get("http://localhost:8080/ms/objectives/e38b1d18-d0c9-434c-9057-960d4878b92d/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                .then();

        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("id", equalTo("e38b1d18-d0c9-434c-9057-960d4878b92d"))
                .body("nameObjective", equalTo("string"))
                .body("descriptionObjective", equalTo("string"))
                .body("idUser", equalTo("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
    }

}
