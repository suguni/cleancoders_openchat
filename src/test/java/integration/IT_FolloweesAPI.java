package integration;

import integration.dsl.OpenChatTestDSL;
import integration.dsl.UserDSL;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;

public class IT_FolloweesAPI extends APITestBase {

    private static UserDSL.ITUser VIVIANE = UserDSL.ITUserBuilder.aUser().withUsername("Viviane").build();
    private static UserDSL.ITUser SAMUEL  = UserDSL.ITUserBuilder.aUser().withUsername("Samuel" ).build();
    private static UserDSL.ITUser OLIVIA  = UserDSL.ITUserBuilder.aUser().withUsername("Olivia" ).build();

    @BeforeEach
    public void initialise() {
        VIVIANE = OpenChatTestDSL.register(VIVIANE);
        SAMUEL  = OpenChatTestDSL.register(SAMUEL);
        OLIVIA  = OpenChatTestDSL.register(OLIVIA);
    }

    @Test public void
    return_all_followees_for_a_given_user() {
        givenVivianeFollows(SAMUEL, OLIVIA);

        Response response = when().get(BASE_URL + "/followings/" + VIVIANE.id() + "/followees");

        OpenChatTestDSL.assertAllUsersAreReturned(response, asList(SAMUEL, OLIVIA));
    }

    private void givenVivianeFollows(UserDSL.ITUser... followees) {
        asList(followees).forEach(followee -> OpenChatTestDSL.createFollowing(VIVIANE, followee));
    }

}
