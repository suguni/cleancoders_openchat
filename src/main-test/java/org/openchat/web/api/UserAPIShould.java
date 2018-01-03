package org.openchat.web.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openchat.core.actions.RetrieveAllUsers;
import org.openchat.core.domain.user.User;
import spark.Request;
import spark.Response;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.openchat.builders.UserBuilder.aUser;

@RunWith(MockitoJUnitRunner.class)
public class UserAPIShould {

    private static final String JSON = "application/json";
    private static final User ALICE = aUser().withUsername("Alice").withAbout("About Alice").build();
    private static final User BOB = aUser().withUsername("Bob").withAbout("About Bob").build();
    private static final User CHARLIE = aUser().withUsername("Charlie").withAbout("About Charlie").build();

    @Mock Request request;
    @Mock Response response;
    @Mock RetrieveAllUsers retrieveAllUsers;

    private UserAPI userAPI;

    @Before
    public void initialise() {
        userAPI = new UserAPI(retrieveAllUsers);
    }

    @Test public void
    return_all_registered_users() {
        List<User> users = asList(ALICE, BOB, CHARLIE);
        given(retrieveAllUsers.execute()).willReturn(users);

        String usersJson = userAPI.allUsers(request, response);

        verifyAllUsersAreReturned(usersJson, users);
        verify(response).status(200);
        verify(response).type(JSON);
    }

    private void verifyAllUsersAreReturned(String usersJson, List<User> users) {
        List<User> returnedUsers = Json.parse(usersJson)
                                        .asArray()
                                        .values()
                                        .stream()
                                        .map(userJson -> userFrom(userJson.asObject()))
                                        .collect(toList());
        assertReturnedUserIs(users.get(0), returnedUsers.get(0));
        assertReturnedUserIs(users.get(1), returnedUsers.get(1));
        assertReturnedUserIs(users.get(2), returnedUsers.get(2));
    }

    private void assertReturnedUserIs(User expectedUser, User returnedUser) {
        assertThat(returnedUser.id()).isEqualTo(expectedUser.id());
        assertThat(returnedUser.username()).isEqualTo(expectedUser.username());
        assertThat(returnedUser.about()).isEqualTo(expectedUser.about());
    }

    private User userFrom(JsonObject userJson) {
        String noPassword = "";
        return new User(userJson.getString("userId", ""),
                        userJson.getString("username", ""),
                        noPassword,
                        userJson.getString("about", ""));
    }


}