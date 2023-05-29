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

    @Scheduled(fixedDelay = 1000)
    public void setAutomatons() {
        List<AutomatonEntity> automatons;
        automatons = autoRepo.getAllAutomatons();
        for (AutomatonEntity automaton : automatons) {
            SensorEntity sens = sensRepo.getSensor(automaton.device_id_sens, automaton.sensor_id_sens);
            float setState;
            if (sens.current_val >= automaton.val_top)
                setState = automaton.state_up;
            else if (sens.current_val <= automaton.val_bot)
                setState = automaton.state_down;
            else
                continue;

            SensorEntity acts = sensRepo.getSensor(automaton.device_id_acts, automaton.sensor_id_acts);
            if(acts.current_val == setState)
                continue;

            acts.current_val = setState;
            if(sensRepo.setSensorValue(acts) != 0)
                msRepo.addMeasurement(new MeasurementEntity(acts.device_id, acts.sensor_id, acts.current_val));
        }
    }
}
