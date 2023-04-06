package org.openchat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenChatController {

    @GetMapping("/status")
    public String status() {
        return "OpenChat: OK!";
    }
}
