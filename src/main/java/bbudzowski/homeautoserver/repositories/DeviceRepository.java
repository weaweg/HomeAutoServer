package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.DeviceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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
        String query = String.format("SELECT * FROM %s WHERE id = '%s'", repoName, device_id);
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return (DeviceEntity) nativeQuery.getSingleResult();
    }

    @Transactional
    public Integer addDevice(DeviceEntity device) {
        String query = "INSERT INTO " + repoName + " VALUES " + device.toQuery();
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer updateDevice(DeviceEntity device) {
        String query = String.format("UPDATE %s SET name = '%s', location = '%s' WHERE device_id = '%s'",
                repoName, device.name, device.location, device.device_id);
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer deleteDevice(String device_id) {
        String query = String.format("DELETE FROM %s WHERE id = '%s'", repoName, device_id);
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return nativeQuery.executeUpdate();
    }
}
