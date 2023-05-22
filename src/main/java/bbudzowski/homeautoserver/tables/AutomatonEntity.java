package bbudzowski.homeautoserver.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "automatons", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})
public class AutomatonEntity {
    @Id
    public String name;
    public String device_id_sens;
    public String sensor_id_sens;
    public Float val;
    public Float hysteresis;
    public String device_id_acts;
    public String sensor_id_acts;
    public Integer state_up;
    public Integer state_down;

    public void copyParams(AutomatonEntity automaton) {
        if(automaton.name != null) name = automaton.name;
        if(automaton.device_id_sens != null) device_id_sens = automaton.device_id_sens;
        if(automaton.sensor_id_sens != null) sensor_id_sens = automaton.sensor_id_sens;
        if(automaton.val != null) val = automaton.val;
        if(automaton.hysteresis != null) hysteresis = automaton.hysteresis;
        if(automaton.device_id_acts != null) device_id_acts = automaton.device_id_acts;
        if(automaton.sensor_id_acts != null) sensor_id_acts = automaton.sensor_id_acts;
        if(automaton.state_up != null) state_up = automaton.state_up;
        if(automaton.state_down != null) state_down = automaton.state_down;
    }
}
