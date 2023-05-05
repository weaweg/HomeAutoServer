package bbudzowski.homeautoserver.tables;

public class Sensor {
    public String device_id;
    public Integer id;
    public Integer data_type;
    public Integer current_state;
    public String units;
    public Integer period;

    public String toQuery() {
        return String.format("('%s', '%s', '%s', '%s', '%s', '%s')",
                this.device_id, this.id, this.data_type, this.current_state, this.units, this.period);
    }
}
