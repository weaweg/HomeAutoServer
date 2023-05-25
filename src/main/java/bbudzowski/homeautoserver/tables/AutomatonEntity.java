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
    private String name;
    private String device_id_sens;
    private String sensor_id_sens;
    private Float val_top;
    private Float val_bot;
    private String device_id_acts;
    private String sensor_id_acts;
    private Integer state_up;
    private Integer state_down;

    public void setParams(@NotNull AutomatonEntity automaton) {
        if(automaton.name != null) name = automaton.name;
        if(automaton.device_id_sens != null) device_id_sens = automaton.device_id_sens;
        if(automaton.sensor_id_sens != null) sensor_id_sens = automaton.sensor_id_sens;
        if(automaton.val_top != null) val_top = automaton.val_top;
        if(automaton.val_bot != null) val_bot = automaton.val_bot;
        if(automaton.device_id_acts != null) device_id_acts = automaton.device_id_acts;
        if(automaton.sensor_id_acts != null) sensor_id_acts = automaton.sensor_id_acts;
        if(automaton.state_up != null) state_up = automaton.state_up;W
        if(automaton.state_down != null) state_down = automaton.state_down;
    }

    public String getName() {
        return name;
    }

    public String getDevice_id_sens() {
        return device_id_sens;
    }

    public String getSensor_id_sens() {
        return sensor_id_sens;
    }

    public Float getVal_top() {
        return val_top;
    }

    public Float getVal_bot() {
        return val_bot;
    }

    public String getDevice_id_acts() {
        return device_id_acts;
    }

    public String getSensor_id_acts() {
        return sensor_id_acts;
    }

    public Integer getState_up() {
        return state_up;
    }

    public Integer getState_down() {
        return state_down;
    }
}
