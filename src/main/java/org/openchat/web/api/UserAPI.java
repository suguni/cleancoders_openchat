package org.openchat.web.api;

import org.openchat.core.actions.RetrieveAllUsers;
import org.openchat.core.domain.user.User;
import org.openchat.web.infrastructure.jsonparsers.UserToJson;
import spark.Request;
import spark.Response;

import java.util.List;

import static org.eclipse.jetty.http.HttpStatus.OK_200;

public class UserAPI {
    private static final String JSON = "application/json";
    private RetrieveAllUsers retrieveAllUsers;

    public UserAPI(RetrieveAllUsers retrieveAllUsers) {
        this.retrieveAllUsers = retrieveAllUsers;
    }

    public String allUsers(Request request, Response response) {
        List<User> users = retrieveAllUsers.execute();
        response.status(OK_200);
        response.type(JSON);
        return UserToJson.jsonFor(users);
    }

}