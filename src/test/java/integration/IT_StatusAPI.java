package integration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class IT_StatusAPI extends APITestBase {

    @Test public void
    return_all_followees_for_a_given_user() {

        Response response = RestAssured.when().get(BASE_URL + "/status");

        Assertions.assertThat(response.getBody().asString()).isEqualTo("OpenChat: OK!");
    }
}
