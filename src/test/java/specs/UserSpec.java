package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.StringContains.containsString;

public class UserSpec {
    public static RequestSpecification UserRequestSpec = new RequestSpecBuilder()
            .setBaseUri("https://reqres.in")
            .setBasePath("/api")
            .setContentType("application/json")
            .addFilter(withCustomTemplates())
            .build();

    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .expectBody("name", equalTo("morpheus"))
            .expectBody("job", equalTo("leader"))
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification unsuccessfulLoginResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .expectBody("error", equalTo("Missing password"))
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification updateUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("name", equalTo("morpheus"))
            .log(STATUS)
            .log(BODY)
            .build();

    public static ResponseSpecification getUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody("data.id", equalTo(2))
            .expectBody("data.email", containsString("@reqres.in"))
            .expectBody("data.first_name", notNullValue())
            .expectBody("data.last_name", notNullValue())
            .log(STATUS)
            .log(BODY)
            .build();
}
