package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.MeasurementsRepository;
import bbudzowski.homeautoserver.tables.MeasurementEntity;
import jakarta.servlet.http.HttpServletRequest;
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

    private final MeasurementsRepository msRepo;

    @Autowired
    public MeasurementsController(MeasurementsRepository msRepo) {
        this.msRepo = msRepo;
    }

    @GetMapping()
    public ResponseEntity<?> getMeasurementsForSensor(
            @RequestParam String device_id, @RequestParam String sensor_id,
            @RequestParam Date startTime, @RequestParam Date endTime,
            HttpServletRequest request) {
        List<MeasurementEntity> measurements = msRepo.getMeasurementsForSensor(device_id, sensor_id, startTime, endTime);
        if (measurements == null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @PostMapping("/local/add")
    public ResponseEntity<?> addMeasurement(@RequestBody MeasurementEntity measurement, HttpServletRequest request) {
        measurement.m_time = new Timestamp(System.currentTimeMillis());
        if (msRepo.addMeasurement(measurement) == 0) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<?> deleteMeasurementsForSensor(@RequestParam String device_id, @RequestParam String sensor_id,
                                                         HttpServletRequest request) {
        if (msRepo.deleteMeasurementsForSensor(device_id, sensor_id) == null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
