package integration;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        IT_RegistrationAPI.class,
        IT_LoginAPI.class,
        IT_TimelineAPI.class,
        IT_UsersAPI.class,
        IT_FolloweesAPI.class,
        IT_WallAPI.class
})
public class APITestSuit {
    public static final String UUID_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
    public static final String DATE_PATTERN = "\\d{4}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d([+-][0-2]\\d:[0-5]\\d|Z)";
}