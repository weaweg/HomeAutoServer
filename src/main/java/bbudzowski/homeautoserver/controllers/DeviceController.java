package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.DeviceRepository;
import bbudzowski.homeautoserver.tables.DeviceEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceRepository devRepo;

    @Autowired
    public DeviceController(DeviceRepository devRepo) {
        this.devRepo = devRepo;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getDevices(HttpServletRequest request) {
        List<DeviceEntity> devices = devRepo.getAllDevices();
        if (devices.isEmpty()) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getDevice(@RequestParam String device_id, HttpServletRequest request) {
        DeviceEntity device = devRepo.getDevice(device_id);
        if (device == null) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request), status);
        }
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PostMapping("/local/add")
    public ResponseEntity<?> addDevice(@RequestBody DeviceEntity device, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CREATED;
        if (devRepo.addDevice(device) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDevice(@RequestBody DeviceEntity device, HttpServletRequest request) {
        DeviceEntity dbDevice = devRepo.getDevice(device.device_id);
        HttpStatus status = HttpStatus.OK;
        if(dbDevice == null)
            status = HttpStatus.NOT_FOUND;
        else {
            dbDevice.copyParams(device);
            if (devRepo.updateDevice(dbDevice) == 0)
                status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }

    @DeleteMapping("/local/delete")
    public ResponseEntity<?> removeDevice(@RequestParam String device_id, HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if (devRepo.deleteDevice(device_id) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }
}
