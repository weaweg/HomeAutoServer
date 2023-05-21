package bbudzowski.homeautoserver;

import bbudzowski.homeautoserver.repositories.AutomatonRepository;
import bbudzowski.homeautoserver.repositories.MeasurementsRepository;
import bbudzowski.homeautoserver.repositories.SensorRepository;
import bbudzowski.homeautoserver.tables.AutomatonEntity;
import bbudzowski.homeautoserver.tables.MeasurementEntity;
import bbudzowski.homeautoserver.tables.SensorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class ScheduledTasks {
    private final AutomatonRepository autoRepo;
    private final SensorRepository sensRepo;
    private final MeasurementsRepository msRepo;

    @Autowired
    public ScheduledTasks(AutomatonRepository autoRepo, SensorRepository sensRepo, MeasurementsRepository msRepo) {
        this.autoRepo = autoRepo;
        this.sensRepo = sensRepo;
        this.msRepo = msRepo;
    }

    @Scheduled(fixedDelay = 100000)
    public void setAutomatons() {
        List<AutomatonEntity> automatons;
        automatons = autoRepo.getAllAutomatons();
        for (AutomatonEntity automaton : automatons) {
            SensorEntity acts = sensRepo.getSensor(automaton.device_id_acts, automaton.sensor_id_acts);
            MeasurementEntity lastSensMs =
                    msRepo.getLastMeasurementForSensor(automaton.device_id_sens, automaton.sensor_id_sens);
            if(lastSensMs == null)
                return;

            int set_state;
            if (lastSensMs.val > automaton.val + automaton.hysteresis)
                set_state = automaton.state_up;
            else if (lastSensMs.val < automaton.val - automaton.hysteresis)
                set_state = automaton.state_down;
            else
                continue;

            if(acts.current_state == set_state)
                continue;

            acts.current_state = set_state;
            sensRepo.changeSensorState(acts);
        }
    }
}
