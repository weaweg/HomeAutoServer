package bbudzowski.homeautoserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sensors")
public class SensorController {

    @GetMapping()
    public ResponseEntity<?> getSensor(@RequestParam(required = false) int id) {
        return null;
    }
}
