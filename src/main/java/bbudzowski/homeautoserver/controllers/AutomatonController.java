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

import java.sql.Timestamp;
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

    @GetMapping("/updateTime")
    public ResponseEntity<?> getUpdateTime(HttpServletRequest request) {
        Timestamp updateTime = autoRepo.getUpdateTime();
        return BaseController.returnUpdateTime(updateTime, request);
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
        SensorEntity sens = sensRepo.getSensor(automaton.getDevice_id_sens(), automaton.getSensor_id_sens());
        SensorEntity acts = sensRepo.getSensor(automaton.getDevice_id_acts(), automaton.getSensor_id_acts());
        HttpStatus status = HttpStatus.CREATED;
        if (sens == null || acts == null)
            status = HttpStatus.NOT_FOUND;
        else if (!acts.isDiscrete())
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        else if (autoRepo.addAutomaton(automaton) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAutomaton(@RequWestBody AutomatonEntity automaton, HttpServletRequest request) {
        AutomatonEntity dbAutomaton = autoRepo.getAutomaton(automaton.getName());
        if(dbAutomaton == null)
            return new ResponseEntity<>(new CustomResponse(HttpStatus.NOT_FOUND, request), HttpStatus.NOT_FOUND);
        dbAutomaton.setParams(automaton);
        if (autoRepo.updateAutomaton(dbAutomaton) == 0)
            return new ResponseEntity<>(new CustomResponse(HttpStatus.BAD_REQUEST, request), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(dbAutomaton, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAutomaton(@RequestParam String name, HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if (autoRepo.deleteAutomaton(name) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }
}
