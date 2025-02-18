package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.filter.log.LogDetail.ALL;

public class UserSpec {

    public static RequestSpecification UserRequestSpec = new RequestSpecBuilder()
            .setContentType("application/json")
            .addFilter(withCustomTemplates())
            .build();

    public static ResponseSpecification responseSpec200Ok = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();

    public static ResponseSpecification responseSpec201Created = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();

    public static ResponseSpecification responseSpec400BadRequest = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(ALL)
            .build();

    public static ResponseSpecification responseSpec204NoContent = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(ALL)
            .build();
}
