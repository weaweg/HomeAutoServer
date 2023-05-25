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

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final SensorRepository sensRepo;
    private final MeasurementsRepository msRepo;

    @Autowired
    public SensorController(SensorRepository sensRepo, MeasurementsRepository msRepo) {
        this.sensRepo = sensRepo;
        this.msRepo = msRepo;
    }

    @GetMapping("/update_time")
    public ResponseEntity<?> getUpdateTime(HttpServletRequest request) {
        Timestamp updateTime = sensRepo.getUpdateTime();
        return BaseController.returnUpdateTime(updateTime, request);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSensors(HttpServletRequest request) {
        List<SensorEntity> sensors = sensRepo.getAllSensors();
        if (sensors.isEmpty()) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(sensors, HttpStatus.OK);
    }

    @GetMapping({"", "/local"})
    public ResponseEntity<?> getSensor(@RequestParam String device_id, @RequestParam String sensor_id,
                                       HttpServletRequest request) {
        SensorEntity sensor = sensRepo.getSensor(device_id, sensor_id);
        if (sensor == null) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(sensor, HttpStatus.OK);
    }

    @PostMapping("/local/add")
    public ResponseEntity<?> addSensor(@RequestBody SensorEntity sens, HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if (sensRepo.addSensor(sens) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSensor(@RequestBody SensorEntity sensor, HttpServletRequest request) {
        SensorEntity dbSensor = sensRepo.getSensor(sensor.device_id, sensor.sensor_id);
        if(dbSensor == null)
            return new ResponseEntity<>(new CustomResponse(HttpStatus.NOT_FOUND, request), HttpStatus.NOT_FOUND);
        dbSensor.setParams(sensor);
        if(sensRepo.updateSensor(dbSensor) == 0)
            return new ResponseEntity<>(new CustomResponse(HttpStatus.BAD_REQUEST, request), HttpStatus.BAD_REQUEST);
        else if(sensor.current_val != null && sensor.discrete)
            if (msRepo.addMeasurement(new MeasurementEntity(
                    sensor.device_id, sensor.sensor_id,sensor.current_val)) == 0)
                return new ResponseEntity<>(new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR, request), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(dbSensor, HttpStatus.OK);
    }
}
