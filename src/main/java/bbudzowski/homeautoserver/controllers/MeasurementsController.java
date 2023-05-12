package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.MeasurementsRepository;
import bbudzowski.homeautoserver.tables.MeasurementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementsController {

    @Autowired
    private final MeasurementsRepository msRepo = new MeasurementsRepository();

    @GetMapping()
    public ResponseEntity<?> getMeasurementsForSensor(
            @RequestParam String device_id, @RequestParam String sensor_id,
            @RequestParam Date startTime, @RequestParam Date endTime) {
        List<MeasurementEntity> measurements = msRepo.getMeasurementsForSensor(device_id, sensor_id, startTime, endTime);
        if (measurements == null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, "/measurements"), status);
        }
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @PostMapping("/local/add")
    public ResponseEntity<?> addMeasurement(@RequestBody MeasurementEntity measurement) {
        measurement.m_time = new Timestamp(System.currentTimeMillis());
        if (msRepo.addMeasurement(measurement) == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, "/measurements/local/add"), status);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
