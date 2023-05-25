package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.DeviceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class DeviceRepository {
    private final String repoName = "devices";
    @PersistenceContext
    private EntityManager em;

    public Timestamp getUpdateTime() {
        String query = "SELECT UPDATE_TIME FROM information_schema.tables" +
                " WHERE TABLE_SCHEMA = 'home_auto' AND TABLE_NAME = ?";
        Query nativeQuery = em.createNativeQuery(query, Timestamp.class);
        nativeQuery.setParameter(1, repoName);
        return (Timestamp) nativeQuery.getSingleResult();
    }

    public List<DeviceEntity> getAllDevices() {
        String query = "SELECT * FROM " + repoName;
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return nativeQuery.getResultList();
    }

    public DeviceEntity getDevice(String device_id) {
        String query = "SELECT * FROM " + repoName + " WHERE device_id = ?";
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        nativeQuery.setParameter(1, device_id);
        try {
            return (DeviceEntity) nativeQuery.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public int addDevice(DeviceEntity device) {
        String query = "INSERT INTO " + repoName + " VALUES (?, ?, ?)";
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        nativeQuery.setParameter(1, device.device_id);
        nativeQuery.setParameter(2, device.name);
        nativeQuery.setParameter(3, device.location);
        return nativeQuery.executeUpdate();
    }

    @Transactional
    public int updateDevice(DeviceEntity device) {
        String query = "UPDATE " + repoName + " SET location = ? WHERE device_id = ?";
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        nativeQuery.setParameter(1, device.location);
        nativeQuery.setParameter(2, device.device_id);
        return nativeQuery.executeUpdate();
    }

    @Transactional
    public int deleteDevice(String device_id) {
        String query = "DELETE FROM " + repoName + " WHERE device_id = ?";
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        nativeQuery.setParameter(1, device_id);
        return nativeQuery.executeUpdate();
    }
}
