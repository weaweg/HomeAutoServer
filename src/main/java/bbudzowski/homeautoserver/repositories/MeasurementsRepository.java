package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.MeasurementEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class MeasurementsRepository {
    @PersistenceContext
    private EntityManager em;
    private final String repoName = "measurements";

    public List<MeasurementEntity> getMeasurementsForSensor(String device_id, String sensor_id, Date startDate, Date endDate) {
        String query = String.format("SELECT * FROM %s  WHERE device_id = '%s' AND sensor_id = '%s' AND m_time BETWEEN '%s' AND '%s'",
                repoName, device_id, sensor_id, startDate, endDate);
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        return nativeQuery.getResultList();
    }

    public MeasurementEntity getLastMeasurementForSensor(String device_id, String sensor_id) {
        String query = String.format("SELECT * FROM %s WHERE device_id = '%s' AND sensor_id = '%s' LIMIT 1",
                repoName, device_id, sensor_id);
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        return (MeasurementEntity) nativeQuery.getSingleResult();
    }

    public Integer addMeasurement(MeasurementEntity measurement) {
        String query = "INSERT INTO " + repoName + " VALUES " + measurement.toQuery();
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        return nativeQuery.executeUpdate();
    }
}
