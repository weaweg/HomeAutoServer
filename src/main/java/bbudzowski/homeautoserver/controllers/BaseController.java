package bbudzowski.homeautoserver.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class BaseController {

    @RequestMapping("/")
    public ResponseEntity<?> dummy(HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    public static ResponseEntity<?> returnUpdateTime(Timestamp updateTime, HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if (updateTime == null) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(new CustomResponse(
                status, request, updateTime.toString()), status);
    }
}
