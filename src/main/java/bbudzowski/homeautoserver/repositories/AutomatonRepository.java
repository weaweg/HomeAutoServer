package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.AutomatonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AutomatonRepository {
    private final String repoName = "automatons";
    @PersistenceContext
    private EntityManager em;

    public List<AutomatonEntity> getAllAutomatons() {
        String query = "SELECT * FROM " + repoName;
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        return nativeQuery.getResultList();
    }
    public AutomatonEntity getAutomaton(String name) {
        String query = "SELECT * FROM " + repoName + " WHERE name = ?";
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        nativeQuery.setParameter(1, name);
        List<AutomatonEntity> tmp = nativeQuery.getResultList();
        return tmp.isEmpty() ? null : tmp.get(0);
    }
    @Transactional
    public Integer addAutomaton(AutomatonEntity automaton) {
        String query = "INSERT INTO " + repoName + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        nativeQuery.setParameter(1, automaton.name);
        nativeQuery.setParameter(2, automaton.device_id_sens);
        nativeQuery.setParameter(3, automaton.sensor_id_sens);
        nativeQuery.setParameter(4, automaton.val);
        nativeQuery.setParameter(5, automaton.hysteresis);
        nativeQuery.setParameter(6, automaton.device_id_acts);
        nativeQuery.setParameter(7, automaton.sensor_id_acts);
        nativeQuery.setParameter(8, automaton.state_up);
        nativeQuery.setParameter(9, automaton.state_down);
        return nativeQuery.executeUpdate();
    }
    @Transactional
    public Integer updateAutomaton(AutomatonEntity automaton) {
        String query = "UPDATE " + repoName +
                " SET name = ?, device_id_sens = ?, sensor_id_sens = ?, val = ?, hysteresis = ?," +
                "device_id_acts = ?, sensor_id_acts = ?, state_up = ?, state_down = ? WHERE name = ?";

        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        nativeQuery.setParameter(1, automaton.name);
        nativeQuery.setParameter(2, automaton.device_id_sens);
        nativeQuery.setParameter(3, automaton.sensor_id_sens);
        nativeQuery.setParameter(4, automaton.val);
        nativeQuery.setParameter(5, automaton.hysteresis);
        nativeQuery.setParameter(6, automaton.device_id_acts);
        nativeQuery.setParameter(7, automaton.sensor_id_acts);
        nativeQuery.setParameter(8, automaton.state_up);
        nativeQuery.setParameter(9, automaton.state_down);
        nativeQuery.setParameter(10, automaton.name);
        return nativeQuery.executeUpdate();
    }
    @Transactional
    public Integer deleteAutomaton(String name) {
        String query = "DELETE FROM " + repoName + " WHERE name = ?";
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        nativeQuery.setParameter(1, name);
        return nativeQuery.executeUpdate();
    }
}
