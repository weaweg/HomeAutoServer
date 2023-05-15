package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "automatons", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class AutomatonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(nullable = false)
    public String name;
    @Column(nullable = false)
    public String device_id_sens;
    @Column(nullable = false)
    public String sensor_id_sens;
    @Column(nullable = false)
    public Float val;
    @Column(nullable = false)
    public Boolean direction;
    @Column(nullable = false)
    public String device_id_acts;
    @Column(nullable = false)
    public String sensor_id_acts;
    @Column(nullable = false)
    public Integer set_state;

    public String toQuery() {
        return String.format("('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                this.name, this.device_id_sens, this.sensor_id_sens, this.val, this.direction,
                this.device_id_acts, this.sensor_id_acts, this.set_state);
    }
}
