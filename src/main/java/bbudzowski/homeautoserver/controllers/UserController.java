package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepo = new UserRepository();
}
