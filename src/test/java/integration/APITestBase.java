package integration;

import org.junit.jupiter.api.BeforeEach;
import org.openchat.OpenChatApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = OpenChatApplication.class)
public class APITestBase {
    public static String BASE_URL;

    @Value("${server.port}")
    private int serverPort;

    @BeforeEach
    void setUp() {
        BASE_URL = "http://localhost:" + serverPort;
    }
}
