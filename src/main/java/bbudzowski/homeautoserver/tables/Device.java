package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

import java.net.Inet4Address;

@Entity
@Table(name = "devices",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "ip_address"),
                @UniqueConstraint(columnNames = "name")
        })
public class Device {
    @Id
    public String device_id;
    @Column(nullable = false)
    public Inet4Address ip_address;
    @Column(nullable = false)
    public String name;
    public String location;

    public String toQuery() {
        return String.format("('%s', '%s', '%s', '%s')",
                this.device_id, this.ip_address, this.name, this.location);
    }
}
