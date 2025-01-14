package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.core.IsEqual.equalTo;

public class UpdateUserSpec {
    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setBasePath("/api")
            .setContentType("application/json")
            .build();

    public static ResponseSpecification updateUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("name", equalTo("morpheus"))
            .build();
}