package integration;

import integration.dsl.OpenChatTestDSL;
import integration.dsl.UserDSL;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;

public class IT_UsersAPI extends APITestBase {

    private static UserDSL.ITUser SANDRO = UserDSL.ITUserBuilder.aUser().withUsername("Sandro").build();
    private static UserDSL.ITUser MASH = UserDSL.ITUserBuilder.aUser().withUsername("Mash").build();
    private static UserDSL.ITUser STEVE = UserDSL.ITUserBuilder.aUser().withUsername("Steve").build();
    private static UserDSL.ITUser PEDRO = UserDSL.ITUserBuilder.aUser().withUsername("Pedro").build();

    @Test
    public void
    return_all_users() {
        SANDRO = OpenChatTestDSL.register(SANDRO);
        MASH = OpenChatTestDSL.register(MASH);
        STEVE = OpenChatTestDSL.register(STEVE);
        PEDRO = OpenChatTestDSL.register(PEDRO);

        Response response = when().get(BASE_URL + "/users");

        OpenChatTestDSL.assertAllUsersAreReturned(response, asList(SANDRO, MASH, STEVE, PEDRO));
    }

}
