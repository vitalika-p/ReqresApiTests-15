package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.*;
import org.junit.jupiter.api.*;
import specs.CreateUserSpec;
import specs.LoginSpec;
import specs.UpdateUserSpec;
import specs.UserByIdSpec;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.DeleteUserSpec.deleteRequestSpec;
import static specs.DeleteUserSpec.deleteResponseSpec;


public class ReqresApiTests extends TestBase{

    @DisplayName("Проверка удаления пользователя")
    @Tag("API")
    @Test
    void deleteUserTest() {
        step("Make request and assert", () ->
                given(deleteRequestSpec)
                        .when()
                        .delete()
                        .then()
                        .spec(deleteResponseSpec)
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
                given(CreateUserSpec.createUserRequestSpec)
                        .body(requestData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(CreateUserSpec.createUserResponseSpec)
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
                given(UserByIdSpec.requestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(UserByIdSpec.getUserResponseSpec)
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
                given(LoginSpec.requestSpec)
                        .body(loginRequest)
                        .when()
                        .post("/login")
                        .then()
                        .spec(LoginSpec.unsuccessfulLoginResponseSpec)
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
                given(UpdateUserSpec.requestSpec)
                        .body(updateUserRequest)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(UpdateUserSpec.updateUserResponseSpec)
                        .extract().as(UpdateUserModel.class)
        );

        step("Проверить данные в ответе", () -> {
            assertThat(updatedUser.getName()).isEqualTo("morpheus");
            assertThat(updatedUser.getJob()).isEqualTo("zion resident");
        });
    }
}