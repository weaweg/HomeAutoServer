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
        if (devices == null) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getDevice(@RequestParam String device_id, HttpServletRequest request) {
        DeviceEntity device = devRepo.getDevice(device_id);
        if (device == null) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
        }
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PostMapping("/local/add")
    public ResponseEntity<?> addDevice(@RequestBody DeviceEntity dv, HttpServletRequest request) {
        if (devRepo.addDevice(dv) == null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
        }
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
    }

    @PatchMapping("/local/update")
    public ResponseEntity<?> updateDevice(@RequestBody DeviceEntity device, HttpServletRequest request) {
        if (devRepo.updateDevice(device) == null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
        }
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
    }

    @DeleteMapping("/local/delete")
    public ResponseEntity<?> removeDevice(@RequestParam String device_id, HttpServletRequest request) {
        if (devRepo.deleteDevice(device_id) == null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
        }
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new CustomResponse(status, request.getContextPath()), status);
    }
}
