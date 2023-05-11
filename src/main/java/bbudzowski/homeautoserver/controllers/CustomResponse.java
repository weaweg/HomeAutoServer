package bbudzowski.homeautoserver.controllers;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CustomResponse {
    public LocalDateTime timestamp;
    public int status;
    public String desc;
    public String path;
    public CustomResponse(HttpStatus status, String path) {
        timestamp = LocalDateTime.now();
        this.status = status.value();
        desc = status.getReasonPhrase();
        this.path = path;
    }
}
