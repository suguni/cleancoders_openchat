package integration;

import com.eclipsesource.json.JsonObject;
import integration.dsl.OpenChatTestDSL;
import integration.dsl.UserDSL;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

public class IT_LoginAPI extends APITestBase {

    private static UserDSL.ITUser ANTONY = UserDSL.ITUserBuilder.aUser().withUsername("Antony").build();

    @BeforeEach
    public void initialise() {
        ANTONY = OpenChatTestDSL.register(ANTONY);
    }

    @Test public void
    perform_login() {
        RestAssured.given()
                .body(withJsonContaining(ANTONY.username(), ANTONY.password()))
        .when()
                .post(BASE_URL + "/login")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", is(ANTONY.id()))
                .body("username", is(ANTONY.username()))
                .body("about", is(ANTONY.about()));
    }

    private String withJsonContaining(String username, String password) {
        return new JsonObject()
                .add("username", username)
                .add("password", password)
                .toString();
    }
}
