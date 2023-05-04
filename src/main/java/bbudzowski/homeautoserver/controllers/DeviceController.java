package bbudzowski.homeautoserver.controllers;

import bbudzowski.homeautoserver.repositories.DeviceRepository;
import bbudzowski.homeautoserver.tables.Device;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("devices")
public class DeviceController {
    private final DeviceRepository deviceRepository;

    public DeviceController() throws SQLException {
        deviceRepository = new DeviceRepository();
    }

    @GetMapping()
    public ResponseEntity<?> getDevice(@RequestParam(required = false) Integer id) {
        List<Device> devices = deviceRepository.getAllDevices();
        if (devices != null)
            return new ResponseEntity<>(devices, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
