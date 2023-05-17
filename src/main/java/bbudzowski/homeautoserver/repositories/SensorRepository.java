package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.SensorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
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
        String query = "SELECT * FROM " + repoName + " WHERE device_id = ? AND sensor_id = ?";
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        nativeQuery.setParameter(1, device_id);
        nativeQuery.setParameter(2, sensor_id);
        List<SensorEntity> tmp = nativeQuery.getResultList();
        return tmp.isEmpty() ? null : tmp.get(0);
    }

    @Transactional
    public Integer addSensor(SensorEntity sensor) {
        String query = "INSERT INTO " + repoName + " VALUES (?, ?, ?, ?, ?)";
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        nativeQuery.setParameter(1, sensor.device_id);
        nativeQuery.setParameter(2, sensor.sensor_id);
        nativeQuery.setParameter(3, sensor.data_type);
        nativeQuery.setParameter(4, sensor.current_state);
        nativeQuery.setParameter(5, sensor.units);
        return nativeQuery.executeUpdate();
    }

    @Transactional
    public Integer changeSensorState(SensorEntity sensor) {
        String query = "UPDATE " + repoName + " SET current_state = ? WHERE device_id = ? AND sensor_id = ?";
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        nativeQuery.setParameter(1, sensor.current_state);
        nativeQuery.setParameter(2, sensor.device_id);
        nativeQuery.setParameter(3, sensor.sensor_id);
        return nativeQuery.executeUpdate();
    }

    @Transactional
    public Integer removeSensor(String device_id, String sensor_id) {
        String query = "DELETE FROM " + repoName + " WHERE device_id = ? AND sensor_id = ?";
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        nativeQuery.setParameter(1, device_id);
        nativeQuery.setParameter(2, sensor_id);
        return nativeQuery.executeUpdate();
    }
}
