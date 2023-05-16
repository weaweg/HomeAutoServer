package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.SensorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SensorRepository {
    private final String repoName = "sensors";
    @PersistenceContext
    private EntityManager em;

    public List<SensorEntity> getAllSensors() {
        String query = "SELECT * FROM " + repoName;
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        return nativeQuery.getResultList();
    }

    public SensorEntity getSensor(String device_id, String sensor_id) {
        String query = String.format("SELECT * FROM %s WHERE device_id = %s AND sensor_id = %s",
                repoName, device_id, sensor_id);
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        List<SensorEntity> tmp = nativeQuery.getResultList();
        return tmp.isEmpty() ? null : tmp.get(0);
    }

    @Transactional
    public Integer addSensor(SensorEntity sens) {
        String query = "INSERT INTO " + repoName + " VALUES " + sens.toQuery();
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Transactional
    public Integer changeSensorState(SensorEntity sensor) {
        String query = String.format("UPDATE %s SET current_state = %s WHERE device_id = %s AND sensor_id = %s",
                repoName, sensor.current_state, sensor.device_id, sensor.sensor_id);
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Transactional
    public Integer removeSensor(String device_id, String sensor_id) {
        String query = String.format("DELETE FROM %s WHERE device_id = %s AND sensor_id = %s",
                repoName, device_id, sensor_id);
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        return nativeQuery.executeUpdate();
    }
}
