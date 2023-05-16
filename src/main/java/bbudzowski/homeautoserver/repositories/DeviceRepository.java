package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.DeviceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceRepository {
    private final String repoName = "devices";
    @PersistenceContext
    private EntityManager em;

    public List<DeviceEntity> getAllDevices() {
        String query = "SELECT * FROM " + repoName;
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return nativeQuery.getResultList();
    }

    public DeviceEntity getDevice(String device_id) {
        String query = "SELECT * FROM " + repoName + " WHERE device_id = ?";
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        nativeQuery.setParameter(1, device_id);
        List<DeviceEntity> tmp = nativeQuery.getResultList();
        return tmp.isEmpty() ? null : tmp.get(0);
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
        String query = "UPDATE " + repoName + " SET name = ?, location = ? WHERE device_id = ?";
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        nativeQuery.setParameter(1, device.name);
        nativeQuery.setParameter(2, device.location);
        nativeQuery.setParameter(3, device.device_id);
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
