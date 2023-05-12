package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.SensorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SensorRepository {
    @PersistenceContext
    EntityManager em;
    String repoName = "sensors";

    public List<SensorEntity> getAllSensors() {
        String query = "SELECT * FROM " + repoName;
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        return nativeQuery.getResultList();
    }

    public SensorEntity getSensor(String device_id, String sensor_id) {
        String query = "SELECT * FROM " + repoName + " WHERE device_id = " + device_id + " AND sensor_id = " + sensor_id;
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        return (SensorEntity) nativeQuery.getSingleResult();
    }

    public Integer addSensor(SensorEntity sens) {
        String query = "INSERT INTO sensors VALUES " + sens.toQuery();
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer updateSensor(SensorEntity sensor) {
        String query = "UPDATE " + repoName + " SET current_state = " + sensor.current_state +
                " WHERE device_id = " + sensor.device_id + " AND sensor_id = " + sensor.sensor_id;
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer removeSensor(String device_id, String sensor_id) {
        String query = "DELETE FROM " + repoName + " WHERE device_id = " + device_id + " AND sensor_id = " + sensor_id;
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        return nativeQuery.executeUpdate();
    }
}
