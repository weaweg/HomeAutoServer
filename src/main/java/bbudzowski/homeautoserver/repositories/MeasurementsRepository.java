package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.MeasurementEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class MeasurementsRepository {
    private final String repoName = "measurements";
    @PersistenceContext
    private EntityManager em;

    public List<MeasurementEntity> getMeasurementsForSensor(String device_id, String sensor_id, String startDate, String endDate) {
        String query = "SELECT * FROM " + repoName + " WHERE device_id = ? AND sensor_id = ? AND m_time BETWEEN ? AND ?";
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        nativeQuery.setParameter(1, device_id);
        nativeQuery.setParameter(2, sensor_id);
        nativeQuery.setParameter(3, startDate);
        nativeQuery.setParameter(4, endDate);
        return nativeQuery.getResultList();
    }

    public MeasurementEntity getLastMeasurementForSensor(String device_id, String sensor_id) {
        String query = "SELECT * FROM " + repoName + " WHERE device_id = ? AND sensor_id = ? ORDER BY m_time DESC LIMIT 1";
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        nativeQuery.setParameter(1, device_id);
        nativeQuery.setParameter(2, sensor_id);
        List<MeasurementEntity> tmp = nativeQuery.getResultList();
        return tmp.isEmpty() ? null : tmp.get(0);
    }

    @Transactional
    public Integer addMeasurement(MeasurementEntity measurement) {
        String query = "INSERT INTO " + repoName + " VALUES (?, ?, ?, ?, ?)";
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        nativeQuery.setParameter(1, null);
        nativeQuery.setParameter(2, measurement.device_id);
        nativeQuery.setParameter(3, measurement.sensor_id);
        nativeQuery.setParameter(4, new Timestamp(System.currentTimeMillis()));
        nativeQuery.setParameter(5, measurement.val);
        return nativeQuery.executeUpdate();
    }

    @Transactional
    public Integer deleteMeasurementsForSensor(String device_id, String sensor_id) {
        String query = "DELETE FROM " + repoName + " WHERE device_id = ? AND sensor_id = ?";
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        nativeQuery.setParameter(1, device_id);
        nativeQuery.setParameter(2, sensor_id);
        return nativeQuery.executeUpdate();
    }

    @Transactional
    public Integer deleteLastMeasurementForSensor(Long id) {
        String query = "DELETE FROM " + repoName + " WHERE id = ?";
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        nativeQuery.setParameter(1, id);
        return nativeQuery.executeUpdate();
    }
}
