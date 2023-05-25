package bbudzowski.homeautoserver.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "automatons", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class AutomatonEntity {
    @Id
    public String name;
    public String device_id_sens;
    public String sensor_id_sens;
    public Float val_top;
    public Float val_bot;
    public String device_id_acts;
    public String sensor_id_acts;
    public Integer state_up;
    public Integer state_down;

    public void setParams(@NotNull AutomatonEntity aut) {
        if(aut.name != null) name = aut.name;
        if(aut.device_id_sens != null) device_id_sens = aut.device_id_sens;
        if(aut.sensor_id_sens != null) sensor_id_sens = aut.sensor_id_sens;
        if(aut.val_top != null) val_top = aut.val_top;
        if(aut.val_bot != null) val_bot = aut.val_bot;
        if(aut.device_id_acts != null) device_id_acts = aut.device_id_acts;
        if(aut.sensor_id_acts != null) sensor_id_acts = aut.sensor_id_acts;
        if(aut.state_up != null) state_up = aut.state_up;
        if(aut.state_down != null) state_down = aut.state_down;
    }
}
