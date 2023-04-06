package org.openchat;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

@RestController
public class SwaggerController {

    @RequestMapping(method = OPTIONS, path = "/users")
    public String users() {
        return "OK";
    }

    @RequestMapping(method = OPTIONS, path = "/login")
    public String login() {
        return "OK";
    }

    @RequestMapping(method = OPTIONS, path = "/users/{userId}/timeline")
    public String timeline(@PathVariable String userId) {
        return "OK";
    }

    @RequestMapping(method = OPTIONS, path = "/followings")
    public String followings() {
        return "OK";
    }

    @RequestMapping(method = OPTIONS, path = "/followings/{userId}/followees")
    public String followees(@PathVariable String userId) {
        return "OK";
    }

    @RequestMapping(method = OPTIONS, path = "/followings/{userId}/wall")
    public String wall(@PathVariable String userId) {
        return "OK";
    }
}
