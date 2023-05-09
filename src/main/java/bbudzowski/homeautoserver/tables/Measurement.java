package bbudzowski.homeautoserver.tables;

import java.sql.Timestamp;

public class Measurement {
    public String device_id;
    public Integer sensor_id;
    public Long m_time;
    public Float val;

    public String toQuery() {
        return String.format("('%s', '%s', '%s', '%s')",
                this.device_id, this.sensor_id, this.m_time, this.val);
    }
}
