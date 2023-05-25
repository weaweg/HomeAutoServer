package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "sensors")
public class SensorEntity {
    @Id
    private String device_id;
    @Id
    private String sensor_id;
    private String name;
    private Boolean discrete;
    private Float current_val;
    private Timestamp m_time;
    private String json_desc;

    public void setParams(@NotNull SensorEntity sensor) {
        if(sensor.name != null) name = sensor.name;
        json_desc = sensor.json_desc;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public String getName() {
        return name;
    }

    public Boolean isDiscrete() {
        return discrete;
    }

    public Float getCurrent_val() {
        return current_val;
    }

    public Timestamp getM_time() {
        return m_time;
    }

    public String getJson_desc() {
        return json_desc;
    }
}
