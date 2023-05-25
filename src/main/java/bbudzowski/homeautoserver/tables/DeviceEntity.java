package bbudzowski.homeautoserver.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "devices", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class DeviceEntity {
    @Id
    public String device_id;
    public String name;
    public String location;

    public void setParams(@NotNull DeviceEntity device) {
        location = device.location;
    }
}
