package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.MeasurementsRepository;
import bbudzowski.homeautoserver.tables.Measurement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsRepository msRepo = new MeasurementsRepository();

    @GetMapping()
    public ResponseEntity<?> getMeasurementsForSensor(
            @RequestParam String device_id, @RequestParam Integer sensor_id,
            @RequestParam(required = false) Timestamp startTime, @RequestParam(required = false) Timestamp endTime) {
        List<Measurement> measurements = null;
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
