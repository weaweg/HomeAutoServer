package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.MeasurementsRepository;
import bbudzowski.homeautoserver.repositories.SensorRepository;
import bbudzowski.homeautoserver.tables.MeasurementEntity;
import bbudzowski.homeautoserver.tables.SensorEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementsController {

    private final MeasurementsRepository msRepo;
    private final SensorRepository sensRepo;

    @Autowired
    public MeasurementsController(MeasurementsRepository msRepo, SensorRepository sensRepo) {
        this.msRepo = msRepo;
        this.sensRepo = sensRepo;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getMeasurementsForSensor(
            @RequestParam String device_id, @RequestParam String sensor_id,
            @RequestParam String start_time, @RequestParam String end_time,
            HttpServletRequest request) {
        List<MeasurementEntity> measurements = msRepo.getMeasurementsForSensor(device_id, sensor_id, start_time, end_time);
        if (measurements.isEmpty()) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @PostMapping("/local/add")
    public ResponseEntity<?> addMeasurement(@RequestBody MeasurementEntity measurement, HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if(msRepo.addMeasurement(measurement) == 0)
            status = HttpStatus.BAD_REQUEST;
        else if(sensRepo.setSensorValue(new SensorEntity(
                measurement.device_id, measurement.sensor_id, measurement.val)) == 0)
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMeasurementsForSensor(@RequestParam String device_id, @RequestParam String sensor_id,
                                                         HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if (msRepo.deleteMeasurementsForSensor(device_id, sensor_id) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    @DeleteMapping("/delete_one")
        public ResponseEntity<?> deleteMeasurement(@RequestParam Long id, HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if(msRepo.deleteMeasurement(id) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
}
