package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.UserRepository;
import bbudzowski.homeautoserver.tables.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepo = new UserRepository();

    @GetMapping("/user/signup")
    public ResponseEntity<?> userSignUp(@RequestBody UserEntity user) {

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
