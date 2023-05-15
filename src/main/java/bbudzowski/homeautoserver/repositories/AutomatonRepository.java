package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.AutomatonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.Modifying;
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
        String query = String.format("SELECT * FROM %s WHERE name = '%s'", repoName, name);
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        try {
            return (AutomatonEntity) nativeQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Integer addAutomaton(AutomatonEntity automaton) {
        String query = "INSERT INTO " + repoName + " VALUES " + automaton.toQuery();
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        try {
            return nativeQuery.executeUpdate();
        } catch (Exception e) {
            return null;
        }
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer updateAutomaton(AutomatonEntity automaton) {
        String query = String.format(
                "UPDATE %s SET name = '%s', device_id_sens = '%s', sensor_id_sens = '%s', val = '%s', direction = '%s'" +
                        "device_id_acts = '%s', sensor_id_acts = '%s', set_state = '%s' WHERE id = '%s'",
                repoName, automaton.name, automaton.device_id_sens, automaton.sensor_id_sens, automaton.val, automaton.direction,
                automaton.device_id_acts, automaton.sensor_id_acts, automaton.set_state, automaton.id);
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer deleteAutomaton(String name) {
        String query = String.format("DELETE FROM %s WHERE name = '%s'", repoName, name);
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        return nativeQuery.executeUpdate();
    }
}
