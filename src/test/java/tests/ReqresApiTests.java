package tests;

import models.*;
import org.junit.jupiter.api.*;
import specs.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqresApiTests extends TestBase {

    @DisplayName("Проверка удаления пользователя")
    @Tag("API")
    @Test
    void deleteUserTest() {
        step("Отправить запрос на удаление пользователя и проверить ответ", () ->
                given(UserSpec.UserRequestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(UserSpec.responseSpec204NoContent)
        );
    }


    @DisplayName("Создание нового пользователя")
    @Tag("API")
    @Test
    void createUserTest() {
        CreateUserModel requestData = step("Подготовить данные для создания пользователя", () -> {
            CreateUserModel data = new CreateUserModel();
            data.setName("morpheus");
            data.setJob("leader");
            return data;
        });

        CreateUserModel responseData = step("Отправить запрос на создание пользователя", () ->
                given(UserSpec.UserRequestSpec)
                        .body(requestData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(UserSpec.responseSpec201Created)
                        .extract().as(CreateUserModel.class)
        );

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.getName()).isEqualTo("morpheus");
            assertThat(responseData.getJob()).isEqualTo("leader");
        });
    }

    @DisplayName("Получение пользователя по ID")
    @Tag("API")
    @Test
    void getUserByIdTest() {
        UserResponseModel responseData = step("Отправить запрос на получение пользователя по ID", () ->
                given(UserSpec.UserRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(UserSpec.responseSpec200Ok)
                        .extract()
                        .as(UserResponseModel.class)
        );

        UserByIdModel userData = responseData.getData();

        step("Проверить данные пользователя", () -> {
            assertThat(userData.getId()).isEqualTo(2);
            assertThat(userData.getEmail()).contains("@reqres.in");
            assertThat(userData.getFirst_name()).isNotEmpty();
            assertThat(userData.getLast_name()).isNotEmpty();
            assertThat(userData.getAvatar()).isNotEmpty();
        });
    }

    @DisplayName("Некорректный логин без пароля")
    @Tag("API")
    @Test
    void unsuccessfulLoginTest() {
        LoginModel loginRequest = step("Подготовить данные для запроса логина", () -> {
            LoginModel request = new LoginModel();
            request.setEmail("eve.holt@reqres.in");
            return request;
        });

        LoginModel errorResponse = step("Отправить запрос с некорректными данными", () ->
                given(UserSpec.UserRequestSpec)
                        .body(loginRequest)
                        .when()
                        .post("/login")
                        .then()
                        .spec(UserSpec.responseSpec400BadRequest)
                        .extract().as(LoginModel.class)
        );

        step("Проверить сообщение об ошибке", () ->
                assertThat(errorResponse.getError()).isEqualTo("Missing password")
        );
    }

    @DisplayName("Обновление пользователя")
    @Tag("API")
    @Test
    void updateUserTest() {
        UpdateUserModel updateUserRequest = step("Подготовить данные для обновления пользователя", () -> {
            UpdateUserModel request = new UpdateUserModel();
            request.setName("morpheus");
            request.setJob("zion resident");
            return request;
        });

        UpdateUserModel updatedUser = step("Отправить запрос на обновление пользователя", () ->
                given(UserSpec.UserRequestSpec)
                        .body(updateUserRequest)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(UserSpec.responseSpec200Ok)
                        .extract().as(UpdateUserModel.class)
        );

        step("Проверить данные в ответе", () -> {
            assertThat(updatedUser.getName()).isEqualTo("morpheus");
            assertThat(updatedUser.getJob()).isEqualTo("zion resident");
        });
    }
}
