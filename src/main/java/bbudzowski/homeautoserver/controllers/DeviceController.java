package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.DeviceRepository;
import bbudzowski.homeautoserver.tables.Device;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceRepository devRepo;

    public DeviceController() {
        devRepo = new DeviceRepository();
    }

    @GetMapping()
    public ResponseEntity<?> getDevice(@RequestParam(required = false) String id) {
        Object devices;
        if (id != null)
            devices = devRepo.getDevice(id);
        else
            devices = devRepo.getAllDevices();
        if (devices == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PutMapping("/local/add")
    public ResponseEntity<?> addDevice(@RequestBody Device dv) {
        if (devRepo.addDevice(dv) == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/local/delete")
    public ResponseEntity<?> removeDevice(@RequestParam String id) {
        if (devRepo.removeDevice(id) == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
