package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.AutomatonRepository;
import bbudzowski.homeautoserver.repositories.SensorRepository;
import bbudzowski.homeautoserver.tables.AutomatonEntity;
import bbudzowski.homeautoserver.tables.SensorEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/automaton")
public class AutomatonController {

    private final AutomatonRepository autoRepo;
    private final SensorRepository sensRepo;

    @Autowired
    public AutomatonController(AutomatonRepository autoRepo, SensorRepository sensRepo) {
        this.autoRepo = autoRepo;
        this.sensRepo = sensRepo;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAutomatons(HttpServletRequest request) {
        List<AutomatonEntity> automatons = autoRepo.getAllAutomatons();
        if (automatons.isEmpty()) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(automatons, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAutomaton(@RequestParam String name, HttpServletRequest request) {
        AutomatonEntity automaton = autoRepo.getAutomaton(name);
        if (automaton == null) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(automaton, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAutomaton(@RequestBody AutomatonEntity automaton, HttpServletRequest request) {
        SensorEntity sens = sensRepo.getSensor(automaton.device_id_sens, automaton.sensor_id_sens);
        SensorEntity acts = sensRepo.getSensor(automaton.device_id_acts, automaton.sensor_id_acts);
        HttpStatus status = HttpStatus.CREATED;
        if (sens == null || acts == null)
            status = HttpStatus.NOT_FOUND;
        else if (sens.data_type >= 1 || acts.data_type <= 0)
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        else if (autoRepo.addAutomaton(automaton) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAutomaton(@RequestBody AutomatonEntity automaton, HttpServletRequest request) {
        AutomatonEntity dbAutomaton = autoRepo.getAutomaton(automaton.name);
        HttpStatus status = HttpStatus.OK;
        if(dbAutomaton == null)
            status = HttpStatus.NOT_FOUND;
        else {
            dbAutomaton.copyParams(automaton);
            if (autoRepo.updateAutomaton(dbAutomaton) == 0)
                status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAutomaton(@RequestParam String name, HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if (autoRepo.deleteAutomaton(name) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }
}
