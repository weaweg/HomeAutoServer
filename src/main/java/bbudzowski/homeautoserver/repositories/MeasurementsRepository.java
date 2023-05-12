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
    EntityManager em;
    String repoName = "measurements";

    public List<MeasurementEntity> getMeasurementsForSensor(String device_id, String sensor_id, Date startDate, Date endDate) {
        String query = "SELECT * FROM " + repoName + " WHERE device_id = " + device_id + " AND sensor_id = " + sensor_id +
                " AND m_time BETWEEN " + startDate + " AND " + endDate;
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        return nativeQuery.getResultList();
    }

    public MeasurementEntity getLastMeasurementForSensor(String device_id, String sensor_id) {
        String query = "SELECT * FROM " + repoName + " WHERE device_id = " + device_id +
                " AND sensor_id = " + sensor_id + " LIMIT 1";
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        return (MeasurementEntity) nativeQuery.getSingleResult();
    }

    public Integer addMeasurement(MeasurementEntity measurement) {
        String query = "INSERT INTO " + repoName + " VALUES " + measurement.toQuery();
        Query nativeQuery = em.createNativeQuery(query, MeasurementEntity.class);
        return nativeQuery.executeUpdate();
    }
}
