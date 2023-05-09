package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.MeasurementsRepository;
import bbudzowski.homeautoserver.tables.Measurement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<Measurement> measurements = msRepo.getAllMeasurementsForSensor(device_id, sensor_id);
        if(measurements == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @PutMapping("/local/add") ResponseEntity<?> addMeasurement(@RequestBody Measurement measurement) {
        measurement.m_time = System.currentTimeMillis();
        if(msRepo.addMeasurement(measurement) == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
