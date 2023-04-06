package integration;

import com.eclipsesource.json.JsonArray;
import integration.dsl.OpenChatTestDSL;
import integration.dsl.PostDSL;
import integration.dsl.UserDSL;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.eclipsesource.json.Json.parse;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;

public class IT_WallAPI extends APITestBase {

    private static UserDSL.ITUser ALICE   = UserDSL.ITUserBuilder.aUser().withUsername("Alice"  ).build();
    private static UserDSL.ITUser BOB     = UserDSL.ITUserBuilder.aUser().withUsername("Bob"    ).build();
    private static UserDSL.ITUser CHARLIE = UserDSL.ITUserBuilder.aUser().withUsername("Charlie").build();
    private static UserDSL.ITUser JULIE   = UserDSL.ITUserBuilder.aUser().withUsername("Julie"  ).build();

    private static PostDSL.ITPost POST_1_ALICE = PostDSL.ITPostBuilder.aPost().withText("Post 1").build();
    private static PostDSL.ITPost POST_2_BOB = PostDSL.ITPostBuilder.aPost().withText("Post 2").build();
    private static PostDSL.ITPost POST_3_CHARLIE = PostDSL.ITPostBuilder.aPost().withText("Post 3").build();
    private static PostDSL.ITPost POST_4_JULIE = PostDSL.ITPostBuilder.aPost().withText("Post 4").build();
    private static PostDSL.ITPost POST_5_ALICE = PostDSL.ITPostBuilder.aPost().withText("Post 5").build();
    private static PostDSL.ITPost POST_6_BOB = PostDSL.ITPostBuilder.aPost().withText("Post 6").build();

    private JsonArray wall;

    @BeforeEach
    public void initialise() {
        ALICE = OpenChatTestDSL.register(ALICE);
        BOB = OpenChatTestDSL.register(BOB);
        CHARLIE = OpenChatTestDSL.register(CHARLIE);
        JULIE = OpenChatTestDSL.register(JULIE);

        POST_1_ALICE   = PostDSL.ITPostBuilder.aPost().withUserId(ALICE  .id()).withText("Post 1").build();
        POST_2_BOB     = PostDSL.ITPostBuilder.aPost().withUserId(BOB    .id()).withText("Post 2").build();
        POST_3_CHARLIE = PostDSL.ITPostBuilder.aPost().withUserId(CHARLIE.id()).withText("Post 3").build();
        POST_4_JULIE   = PostDSL.ITPostBuilder.aPost().withUserId(JULIE  .id()).withText("Post 4").build();
        POST_5_ALICE   = PostDSL.ITPostBuilder.aPost().withUserId(ALICE  .id()).withText("Post 5").build();
        POST_6_BOB     = PostDSL.ITPostBuilder.aPost().withUserId(BOB    .id()).withText("Post 6").build();
    }
    
    @Test public void
    return_a_wall_containing_posts_from_the_user_and_her_followees() {
        givenPosts(POST_1_ALICE,
                   POST_2_BOB,
                   POST_3_CHARLIE,
                   POST_4_JULIE,
                   POST_5_ALICE,
                   POST_6_BOB);
        andAliceFollows(BOB, CHARLIE);

        whenAliceChecksHerWall();

        thenSheSeesThePosts(POST_6_BOB,
                            POST_5_ALICE,
                            POST_3_CHARLIE,
                            POST_2_BOB,
                            POST_1_ALICE);
    }

    private void givenPosts(PostDSL.ITPost... posts) {
        asList(posts).forEach(OpenChatTestDSL::create);
    }

    private void andAliceFollows(UserDSL.ITUser... followees) {
        asList(followees).forEach(followee -> OpenChatTestDSL.createFollowing(ALICE, followee));
    }

    private void whenAliceChecksHerWall() {
        Response response = when().get(BASE_URL + "/users/" + ALICE.id() + "/wall");
        wall = parse(response.asString()).asArray();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.contentType()).isEqualTo("application/json");
    }

    private void thenSheSeesThePosts(PostDSL.ITPost... posts) {
        for (int index = 0; index < posts.length; index++) {
            OpenChatTestDSL.assertThatJsonPostMatchesPost(wall.get(index), posts[index]);
        }
    }

}
