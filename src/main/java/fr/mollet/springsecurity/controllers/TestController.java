package fr.mollet.springsecurity.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fr.mollet.springsecurity.config.Constants.*;

@RestController
@RequestMapping("/api/test")
@Slf4j
@CrossOrigin(origins = "*") // TODO: handle CORS in a better way
public class TestController {

    @GetMapping("/public")
    public String publicContent() {
        return "Public content";
    }

    @GetMapping
    public String privateContent() {
        return "Private content";
    }

    @GetMapping("/user")
    @Secured({ROLE_USER})
    public String userContent() {
        return "User content";
    }

    @GetMapping("/admin")
    @Secured({ROLE_ADMIN})
    public String adminContent() {
        return "Admin content";
    }

    @GetMapping("/manager")
    @Secured({ROLE_MANAGER})
    public String managerContent() {
        return "Manager content";
    }

}
