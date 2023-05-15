package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "devices", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class DeviceEntity {
    @Id
    public String device_id;
    @Column(nullable = false)
    public String name;
    public String location;

    public String toQuery() {
        return String.format("('%s', '%s', '%s')",
                this.device_id, this.name, this.location);
    }
}
