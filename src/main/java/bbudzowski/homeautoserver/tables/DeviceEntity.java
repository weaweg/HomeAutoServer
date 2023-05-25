package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "devices", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class DeviceEntity {
    @Id
    private String device_id;
    private String name;
    private String location;

    public DeviceEntity() {}

    public void setParams(@NotNull DeviceEntity device) {
        location = device.location;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
