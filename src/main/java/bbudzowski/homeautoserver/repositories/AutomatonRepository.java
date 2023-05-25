package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.AutomatonEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class AutomatonRepository {
    private final String repoName = "automatons";
    @PersistenceContext
    private EntityManager em;

    public Timestamp getUpdateTime() {
        String query = "SELECT UPDATE_TIME FROM information_schema.tables" +
                " WHERE TABLE_SCHEMA = 'home_auto' AND TABLE_NAME = ?";
        Query nativeQuery = em.createNativeQuery(query, Timestamp.class);
        nativeQuery.setParameter(1, repoName);
        return (Timestamp) nativeQuery.getSingleResult();
    }

    public List<AutomatonEntity> getAllAutomatons() {
        String query = "SELECT * FROM " + repoName;
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        return nativeQuery.getResultList();
    }
    public AutomatonEntity getAutomaton(String name) {
        String query = "SELECT * FROM " + repoName + " WHERE name = ?";
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        nativeQuery.setParameter(1, name);
        try {
            return (AutomatonEntity) nativeQuery.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }
    @Transactional
    public Integer addAutomaton(AutomatonEntity automaton) {
        String query = "INSERT INTO " + repoName + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        setFullQuery(automaton, nativeQuery);
        return nativeQuery.executeUpdate();
    }
    @Transactional
    public Integer updateAutomaton(AutomatonEntity automaton) {
        String query = "UPDATE " + repoName +
                " SET name = ?, device_id_sens = ?, sensor_id_sens = ?, val_top = ?, val_bot = ?," +
                "device_id_acts = ?, sensor_id_acts = ?, state_up = ?, state_down = ? WHERE name = ?";
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        setFullQuery(automaton, nativeQuery);
        nativeQuery.setParameter(10, automaton.name);
        return nativeQuery.executeUpdate();
    }

    private void setFullQuery(AutomatonEntity automaton, Query nativeQuery) {
        nativeQuery.setParameter(1, automaton.name);
        nativeQuery.setParameter(2, automaton.device_id_sens);
        nativeQuery.setParameter(3, automaton.sensor_id_sens);
        nativeQuery.setParameter(4, automaton.val_top);
        nativeQuery.setParameter(5, automaton.val_bot);
        nativeQuery.setParameter(6, automaton.device_id_acts);
        nativeQuery.setParameter(7, automaton.sensor_id_acts);
        nativeQuery.setParameter(8, automaton.state_up);
        nativeQuery.setParameter(9, automaton.state_down);
    }

    @Transactional
    public Integer deleteAutomaton(String name) {
        String query = "DELETE FROM " + repoName + " WHERE name = ?";
        Query nativeQuery = em.createNativeQuery(query, AutomatonEntity.class);
        nativeQuery.setParameter(1, name);
        return nativeQuery.executeUpdate();
    }
}
