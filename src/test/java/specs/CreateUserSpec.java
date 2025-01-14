package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserSpec {

    public static RequestSpecification createUserRequestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setBasePath("/api")
            .setContentType("application/json")
            .build();

    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .expectBody("name", equalTo("morpheus"))
            .expectBody("job", equalTo("leader"))
            .build();
}