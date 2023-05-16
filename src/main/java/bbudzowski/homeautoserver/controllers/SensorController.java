package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.SensorRepository;
import bbudzowski.homeautoserver.tables.SensorEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final SensorRepository sensRepo;

    @Autowired
    public SensorController(SensorRepository sensRepo) {
        this.sensRepo = sensRepo;
    }

    @GetMapping()
    public ResponseEntity<?> getSensor(@RequestParam(required = false) String device_id,
                                       @RequestParam(required = false) String sensor_id,
                                       HttpServletRequest request) {
        Object sensors;
        if (sensor_id != null && device_id != null)
            sensors = sensRepo.getSensor(device_id, sensor_id);
        else
            sensors = sensRepo.getAllSensors();
        if (sensors == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }

        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

    @PostMapping("/local/add")
    public ResponseEntity<?> addSensor(@RequestBody SensorEntity sens, HttpServletRequest request) {
        if (sensRepo.addSensor(sens) == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/changeState")
    public ResponseEntity<?> changeState(@RequestBody SensorEntity sens, HttpServletRequest request) {
        if(sensRepo.changeSensorState(sens) == null)  {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/local/delete")
    public ResponseEntity<?> removeSensor(@RequestParam String device_id,
                                          @RequestParam String sensor_id,
                                          HttpServletRequest request) {
        if (sensRepo.removeSensor(device_id, sensor_id) == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
