package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "sensors")
public class SensorEntity {
    @Id
    public String device_id;
    @Id
    public String sensor_id;
    public Integer data_type;
    public Integer current_state;
    public String units;

    public String toQuery() {
        return String.format("(%s, %s, %s, %s, %s)",
                this.device_id, this.sensor_id, this.data_type, this.current_state, this.units);
    }
}
