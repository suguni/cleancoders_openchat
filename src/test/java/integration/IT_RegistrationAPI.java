package integration;

import com.eclipsesource.json.JsonObject;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class IT_RegistrationAPI extends APITestBase {

    @Test public void
    register_a_new_user() {
        RestAssured.given()
                .body(withJsonContaining("Lucy", "alki324d", "About Lucy"))
        .when()
                .post(BASE_URL + "/users")
        .then()
                .statusCode(201)
                .contentType(JSON)
                .body("id", Matchers.matchesPattern(APITestSuit.UUID_PATTERN))
                .body("username", is("Lucy"))
                .body("about", is("About Lucy"));
    }

    private String withJsonContaining(String username, String password, String about) {
        return new JsonObject()
                        .add("username", username)
                        .add("password", password)
                        .add("about", about)
                        .toString();
    }
}
