package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "devices", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class DeviceEntity {
    @Id
    public String device_id;
    public String name;
    public String location;

    public void copyParams(DeviceEntity device) {
        if(device.name != null) name = device.name;
        if(device.location != null) location = device.location;
    }
}
