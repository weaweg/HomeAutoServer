package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.CustomResponse;
import bbudzowski.homeautoserver.repositories.SensorRepository;
import bbudzowski.homeautoserver.tables.Sensor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorRepository sensRepo = new SensorRepository();

    @GetMapping()
    public ResponseEntity<?> getSensor(@RequestParam(required = false) String device_id, @RequestParam(required = false) Integer id) {
        Object sensors;
        if (id != null && device_id != null)
            sensors = sensRepo.getSensor(device_id, id);
        else
            sensors = sensRepo.getAllSensors();
        if (sensors == null){
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, "/sensors"), status);
        }

        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

    @PutMapping("/local/add")
    public ResponseEntity<?> addSensor(@RequestBody Sensor sens) {
        if (sensRepo.addSensor(sens) == null){
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, "/sensors/local/add"), status);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/local/delete")
    public ResponseEntity<?> removeSensor(@RequestParam String device_id, @RequestParam Integer id) {
        if (sensRepo.removeSensor(device_id, id) == null){
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, "/sensors/local/delete"), status);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
