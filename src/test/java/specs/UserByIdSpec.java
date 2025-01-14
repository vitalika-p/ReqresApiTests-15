package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class UserByIdSpec {

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setBasePath("/api")
            .setContentType("application/json")
            .build();

    public static ResponseSpecification getUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("data.id", equalTo(2)) // Проверка id внутри data
            .expectBody("data.email", containsString("@reqres.in")) // Проверка email внутри data
            .expectBody("data.first_name", notNullValue()) // Проверка, что first_name не null
            .expectBody("data.last_name", notNullValue()) // Проверка, что last_name не null
            .build();
}