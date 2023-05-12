package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.AutomatonRepository;
import bbudzowski.homeautoserver.tables.AutomatonEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/automaton")
public class AutomatonController {

    @Autowired
    private final AutomatonRepository autoRepo = new AutomatonRepository();

    @GetMapping("/all")
    public ResponseEntity<?> getAutomatons(HttpServletRequest request) {
        List<AutomatonEntity> automatons = autoRepo.getAllAutomatons();
        if (automatons == null) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
        }
        return new ResponseEntity<>(automatons, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAutomaton(@RequestParam String name, HttpServletRequest request) {
        AutomatonEntity automaton = autoRepo.getAutomaton(name);
        if (automaton == null) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
        }
        return new ResponseEntity<>(automaton, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAutomaton(@RequestBody AutomatonEntity automaton, HttpServletRequest request) {
        if(autoRepo.addAutomaton(automaton) == null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
        }
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateAutomaton(@RequestBody AutomatonEntity automaton, HttpServletRequest request) {
        if (autoRepo.updateAutomaton(automaton) == null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
        }
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAutomaton(@RequestParam String name, HttpServletRequest request) {
        if (autoRepo.deleteAutomaton(name) == null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
        }
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
    }
}
