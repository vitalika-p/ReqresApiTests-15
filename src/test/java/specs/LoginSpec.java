package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoginSpec {
    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setBasePath("/api")
            .setContentType("application/json")
            .build();

    public static ResponseSpecification unsuccessfulLoginResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .expectBody("error", equalTo("Missing password"))
            .build();
}
