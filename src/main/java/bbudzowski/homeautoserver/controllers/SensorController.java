package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.SensorRepository;
import bbudzowski.homeautoserver.tables.SensorEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final SensorRepository sensRepo;

    @Autowired
    public SensorController(SensorRepository sensRepo) {
        this.sensRepo = sensRepo;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSensors(HttpServletRequest request) {
        List<SensorEntity> sensors = sensRepo.getAllSensors();
        if (sensors.isEmpty()) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getSensor(@RequestParam String device_id, @RequestParam String sensor_id,
                                       HttpServletRequest request) {
        SensorEntity sensors = sensRepo.getSensor(device_id, sensor_id);
        if (sensors == null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

    @PostMapping("/local/add")
    public ResponseEntity<?> addSensor(@RequestBody SensorEntity sens, HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if (sensRepo.addSensor(sens) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    @PutMapping("/change_state")
    public ResponseEntity<?> changeSensorState(@RequestBody SensorEntity sens, HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        SensorEntity dbSens = sensRepo.getSensor(sens.device_id, sens.sensor_id);
        if(dbSens == null)
            status = HttpStatus.NOT_FOUND;
        else if (sens.current_state == null || dbSens.data_type == 0){
            status = HttpStatus.BAD_REQUEST;
        }
        else {
            dbSens.copyParams(sens);
            if (sensRepo.changeSensorState(dbSens) == 0)
                status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    @DeleteMapping("/local/delete")
    public ResponseEntity<?> removeSensor(@RequestParam String device_id, @RequestParam String sensor_id,
                                          HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if (sensRepo.removeSensor(device_id, sensor_id) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }
}
