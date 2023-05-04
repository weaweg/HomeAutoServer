package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.DeviceRepository;
import bbudzowski.homeautoserver.tables.Device;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceRepository deviceRepository;

    public DeviceController() throws SQLException {
        deviceRepository = new DeviceRepository();
    }

    @GetMapping()
    public ResponseEntity<?> getDevice(@RequestParam(required = false) Integer id) {
        List<Device> devices = deviceRepository.getAllDevices();
        if (devices == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PutMapping("/local/add")
    public ResponseEntity<?> addDevice(@RequestBody Device dv) {
        if(deviceRepository.addDevice(dv) == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/local/delete")
    public ResponseEntity<?> removeDevice(@RequestParam String id) {
        if(deviceRepository.removeDevice(id) == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
