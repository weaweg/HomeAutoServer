package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.AutomateRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/automate")
public class AutomateController {
    private final AutomateRepository devRepo = new AutomateRepository();
}
