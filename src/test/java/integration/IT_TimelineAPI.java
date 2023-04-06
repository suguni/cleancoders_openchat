package integration;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import integration.dsl.OpenChatTestDSL;
import integration.dsl.PostDSL;
import integration.dsl.UserDSL;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.reverse;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;

public class IT_TimelineAPI extends APITestBase {

    private static UserDSL.ITUser DAVID = UserDSL.ITUserBuilder.aUser().withUsername("David").build();

    private JsonArray timeline;
    private List<PostDSL.ITPost> POSTS;

    @BeforeEach
    public void initialise() {
        DAVID = OpenChatTestDSL.register(DAVID);
        POSTS = createPostsFor(DAVID, 2);
    }

    @Test public void
    retrieve_a_timeline_with_all_posts_from_a_user_in_reverse_chronological_order() {
        givenDavidPosts(POSTS);

        whenHeChecksHisTimeline();

        thenHeShouldSee(reverse(POSTS));
    }

    private List<PostDSL.ITPost> createPostsFor(UserDSL.ITUser user, int numberOfPosts) {
        List<PostDSL.ITPost> posts = new ArrayList<>();
        for (int i = 0; i < numberOfPosts; i++) {
            PostDSL.ITPost post = PostDSL.ITPostBuilder.aPost().withUserId(user.id()).withText("Post " + i).build();
            posts.add(post);
        }
        return posts;
    }

    private void givenDavidPosts(List<PostDSL.ITPost> posts) {
        posts.forEach(OpenChatTestDSL::create);
    }

    private void whenHeChecksHisTimeline() {
        Response response = when().get(BASE_URL + "/users/" + DAVID.id() + "/timeline");
        timeline = Json.parse(response.asString()).asArray();

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.contentType()).isEqualTo(JSON.toString());
    }

    private void thenHeShouldSee(List<PostDSL.ITPost> posts) {
        for (int index = 0; index < posts.size(); index++) {
            OpenChatTestDSL.assertThatJsonPostMatchesPost(timeline.get(index), posts.get(index));
        }
    }

}
