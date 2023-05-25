package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.DeviceRepository;
import bbudzowski.homeautoserver.tables.DeviceEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceRepository devRepo;

    @Autowired
    public DeviceController(DeviceRepository devRepo) {
        this.devRepo = devRepo;
    }

    @GetMapping("/updateTime")
    public ResponseEntity<?> getUpdateTime(HttpServletRequest request) {
        Timestamp updateTime = devRepo.getUpdateTime();
        return BaseController.returnUpdateTime(updateTime, request);
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

    @GetMapping({"", "/local"})
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
        DeviceEntity dbDevice = devRepo.getDevice(device.getDevice_id());
        if(dbDevice == null)
            return new ResponseEntity<>(new CustomResponse(HttpStatus.NOT_FOUND, request), HttpStatus.NOT_FOUND);
        dbDevice.setParams(device);
        if (devRepo.updateDevice(dbDevice) == 0)
            return new ResponseEntity<>(new CustomResponse(HttpStatus.BAD_REQUEST, request), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(dbDevice, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDevice(@RequestParam String device_id, HttpServletRequest request) {
        HttpStatus status = HttpStatus.OK;
        if (devRepo.deleteDevice(device_id) == 0)
            status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new CustomResponse(status, request), status);
    }
}
