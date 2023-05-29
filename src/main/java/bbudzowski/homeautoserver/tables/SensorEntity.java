package bbudzowski.homeautoserver.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "sensors")
public class SensorEntity {
    @Id
    public String device_id;
    @Id
    public String sensor_id;
    public String name;
    public Boolean discrete;
    public Float current_val;
    public Timestamp m_time;
    public String json_desc;

    public SensorEntity() {}

    public SensorEntity(String device_id, String sensor_id, Float current_val) {
        this.device_id = device_id;
        this.sensor_id = sensor_id;
        this.current_val = current_val;
    }

    public void setParams(@NotNull SensorEntity sensor) {
        if(sensor.name != null) name = sensor.name;
        if(sensor.current_val != null && sensor.discrete) {
            current_val = sensor.current_val;
            m_time = new Timestamp(System.currentTimeMillis());
        }
        if(sensor.json_desc != null) json_desc = sensor.json_desc;
    }
}
