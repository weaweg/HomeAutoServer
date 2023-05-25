package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.DeviceEntity;
import bbudzowski.homeautoserver.tables.SensorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class SensorRepository {
    private final String repoName = "sensors";
    @PersistenceContext
    private EntityManager em;

    public Timestamp getUpdateTime() {
        String query = "SELECT UPDATE_TIME FROM information_schema.tables" +
                " WHERE TABLE_SCHEMA = 'home_auto' AND TABLE_NAME = ?";
        Query nativeQuery = em.createNativeQuery(query, Timestamp.class);
        nativeQuery.setParameter(1, repoName);
        return (Timestamp) nativeQuery.getSingleResult();
    }

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
        try {
            return (SensorEntity) nativeQuery.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Integer addSensor(SensorEntity sensor) {
        String query = "INSERT INTO " + repoName + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        nativeQuery.setParameter(1, sensor.getDevice_id());
        nativeQuery.setParameter(2, sensor.getSensor_id());
        nativeQuery.setParameter(3, sensor.getName());
        nativeQuery.setParameter(4, sensor.isDiscrete());
        nativeQuery.setParameter(5, null);
        nativeQuery.setParameter(6, null);
        nativeQuery.setParameter(7, sensor.getJson_desc());
        return nativeQuery.executeUpdate();
    }

    @Transactional
    public Integer updateSensor(SensorEntity sensor) {
        String query = "UPDATE " + repoName + " SET name = ?, json_desc = ? WHERE device_id = ? AND sensor_id = ?";
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        nativeQuery.setParameter(1, sensor.getName());
        nativeQuery.setParameter(2, sensor.getJson_desc());
        nativeQuery.setParameter(3, sensor.getDevice_id());
        nativeQuery.setParameter(3, sensor.getSensor_id());
        return nativeQuery.executeUpdate();
    }

    @Transactional
    public Integer setSensorValue(SensorEntity sensor) {
        String query = "UPDATE " + repoName + " SET current_val = ?, m_time = ? WHERE device_id = ? AND sensor_id = ?";
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        nativeQuery.setParameter(1, sensor.getCurrent_val());
        nativeQuery.setParameter(2, sensor.getM_time());
        nativeQuery.setParameter(3, sensor.getDevice_id());
        nativeQuery.setParameter(3, sensor.getSensor_id());
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
