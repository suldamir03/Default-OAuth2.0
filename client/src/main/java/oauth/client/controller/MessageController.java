package oauth.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    @GetMapping("/")
    public String getMessages() {
        return "Hello %s mf!";
    }

    @GetMapping("/secured")
    public String getMessages(Authentication authentication) {
        System.out.println("auth.getPrincipal: " + authentication.getPrincipal());
        System.out.println("auth: " + authentication.getName());
        return String.format("Hello %s mf!", authentication.getName());
    }


}
