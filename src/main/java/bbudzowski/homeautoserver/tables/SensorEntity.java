package bbudzowski.homeautoserver.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public void copyParams(SensorEntity sensor) {
        if(sensor.data_type != null) data_type = sensor.data_type;
        if(sensor.current_state != null) current_state = sensor.current_state;
        if(sensor.units != null) units = sensor.units;
    }
}
