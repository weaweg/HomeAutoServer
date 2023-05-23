package bbudzowski.homeautoserver.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BaseController {

    @GetMapping()
    public ResponseEntity<?> dummy(HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }
}
