package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "measurements")
public class MeasurementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    public String device_id;
    @Column(nullable = false)
    public String sensor_id;
    @Column(nullable = false)
    public Timestamp m_time;
    @Column(nullable = false)
    public Float val;

    public String toQuery() {
        return String.format("('%s', '%s', '%s', '%s')",
                this.device_id, this.sensor_id, this.m_time, this.val);
    }
}
