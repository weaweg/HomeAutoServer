package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "measurements")
public class MeasurementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String device_id;
    private String sensor_id;
    private Timestamp m_time;
    private Float val;

    public Long getId() {
        return id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public Timestamp getM_time() {
        return m_time;
    }

    public Float getVal() {
        return val;
    }
}

