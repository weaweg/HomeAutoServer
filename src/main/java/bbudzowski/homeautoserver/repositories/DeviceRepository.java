package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.DeviceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceRepository {
    @PersistenceContext
    EntityManager em;
    String repoName = "devices";

    public List<DeviceEntity> getAllDevices() {
        String query = "SELECT * FROM " + repoName;
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return nativeQuery.getResultList();
    }

    public DeviceEntity getDevice(String device_id) {
        String query = "SELECT * FROM " + repoName + " WHERE id = " + device_id;
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return (DeviceEntity) nativeQuery.getSingleResult();
    }

    public Integer addDevice(DeviceEntity device) {
        String query = "INSERT INTO " + repoName + " VALUES " + device.toQuery();
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer updateDevice(DeviceEntity device) {
        String query = "UPDATE " + repoName +
                " SET ip_address = " + device.ip_address +
                ",name = " + device.name +
                ",location = " + device.location +
                " WHERE device_id = " + device.device_id;
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer deleteDevice(String device_id) {
        String query = "DELETE FROM " + repoName + " WHERE id = " + device_id;
        Query nativeQuery = em.createNativeQuery(query, DeviceEntity.class);
        return nativeQuery.executeUpdate();
    }
}
