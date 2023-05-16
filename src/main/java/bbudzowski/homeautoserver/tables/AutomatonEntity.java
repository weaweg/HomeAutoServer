package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "automatons", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class AutomatonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String name;
    public String device_id_sens;
    public String sensor_id_sens;
    public Float val;
    public Integer direction;
    public String device_id_acts;
    public String sensor_id_acts;
    public Integer set_state;

    public String toQuery() {
        return String.format("(%s', %s, %s, %s, %s, %s, %s, %s)",
                this.name, this.device_id_sens, this.sensor_id_sens, this.val, this.direction,
                this.device_id_acts, this.sensor_id_acts, this.set_state);
    }
}
