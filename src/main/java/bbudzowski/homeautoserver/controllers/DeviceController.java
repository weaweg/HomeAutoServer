package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.DeviceRepository;
import bbudzowski.homeautoserver.tables.Device;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceRepository devRepo = new DeviceRepository();

    @GetMapping()
    public ResponseEntity<?> getDevice(@RequestParam(required = false) String id) {
        Object devices;
        if (id != null)
            devices = devRepo.getDevice(id);
        else
            devices = devRepo.getAllDevices();
        if (devices == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, "/devices"), status);
        }
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PutMapping("/local/add")
    public ResponseEntity<?> addDevice(@RequestBody Device dv) {
        String path = "/devices/local/add";
        if(devRepo.addDevice(dv) == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, path), status);
        }
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new CustomResponse(status, path), status);
    }

    @DeleteMapping("/local/delete")
    public ResponseEntity<?> removeDevice(@RequestParam String id) {
        String path = "/devices/local/delete";
        if (devRepo.removeDevice(id) == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, path), status);
        }
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new CustomResponse(status, path), status);
    }

    @PatchMapping("/local/update")
    public ResponseEntity<?> updateDevicesIP(@RequestParam String id, @RequestParam String ip) {
        String path = "/devices/local/delete";
        if(devRepo.updateDevicesIP(id, ip) == null) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new CustomResponse(status, path), status);
        }
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new CustomResponse(status, path), status);
    }
}
