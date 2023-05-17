package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "measurements")
public class MeasurementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String device_id;
    public String sensor_id;
    public Timestamp m_time;
    public Float val;
}
