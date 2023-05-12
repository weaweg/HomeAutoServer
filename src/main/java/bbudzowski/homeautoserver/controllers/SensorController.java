package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.SensorRepository;
import bbudzowski.homeautoserver.tables.SensorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    @Autowired
    private final SensorRepository sensRepo = new SensorRepository();

    @GetMapping()
    public ResponseEntity<?> getSensor(@RequestParam(required = false) String device_id, @RequestParam(required = false) String sensor_id) {
        Object sensors;
        if (sensor_id != null && device_id != null)
            sensors = sensRepo.getSensor(device_id, sensor_id);
        else
            sensors = sensRepo.getAllSensors();
        if (sensors == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, "/sensors"), status);
        }

        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

    @PutMapping("/local/add")
    public ResponseEntity<?> addSensor(@RequestBody SensorEntity sens) {
        if (sensRepo.addSensor(sens) == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, "/sensors/local/add"), status);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/local/delete")
    public ResponseEntity<?> removeSensor(@RequestParam String device_id, @RequestParam String sensor_id) {
        if (sensRepo.removeSensor(device_id, sensor_id) == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, "/sensors/local/delete"), status);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
