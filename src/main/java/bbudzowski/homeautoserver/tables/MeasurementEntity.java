package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "measurements")
public class MeasurementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String device_id;
    public String sensor_id;
    public Timestamp m_time;
    public Float val;

    public String toQuery() {
        return String.format("(%s, %s, %s, %s)",
                this.device_id, this.sensor_id, this.m_time, this.val);
    }
}
