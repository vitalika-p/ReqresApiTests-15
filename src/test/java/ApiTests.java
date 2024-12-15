import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    @BeforeAll
    public static void preconditionsForAllTests() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    //Проверка удаления пользователя
    @Test
    void deleteUserTest() {
        when()
                .delete("/users/2")
                .then()
                .statusCode(204)
                .body(isEmptyString());
    }

    //Проверка успешного создания пользователя
    @Test
    void createUserTest() {
        String requestBody = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"));
    }

    //Проверка получения пользователя по ID
    @Test
    void getUserByIdTest() {
        given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", containsString("@reqres.in"));
    }

    //Проверка логина с некорректными данными
    @Test
    void unsuccessfulLoginTest() {
        String requestBody = "{\"email\": \"eve.holt@reqres.in\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    //Проверка обновления данных пользователя
    @Test
    void updateUserTest() {
        String requestBody = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));
    }
}