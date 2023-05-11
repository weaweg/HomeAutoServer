package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    public String device_id;
    @Id
    public String sensor_id;
    @Column(nullable = false)
    public Integer data_type;
    @Column(nullable = false)
    public Integer current_state;
    @Column(nullable = false)
    public String units;
    @Column(nullable = false)
    public Integer period;

    public String toQuery() {
        return String.format("('%s', '%s', '%s', '%s', '%s', '%s')",
                this.device_id, this.sensor_id, this.data_type, this.current_state, this.units, this.period);
    }
}
