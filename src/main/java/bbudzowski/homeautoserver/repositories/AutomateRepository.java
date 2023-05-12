package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.AutomateEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AutomateRepository {
    @PersistenceContext
    EntityManager em;
    String repoName = "automates";

    public List<AutomateEntity> getAllAutomates() {
        String query = "SELECT * FROM " + repoName;
        Query nativeQuery = em.createNativeQuery(query, AutomateEntity.class);
        return nativeQuery.getResultList();
    }

    public AutomateEntity getAutomate(String name) {
        String query = "SELECT * FROM " + repoName + " WHERE name = " + name;
        Query nativeQuery = em.createNativeQuery(query, AutomateEntity.class);
        return (AutomateEntity) nativeQuery.getSingleResult();
    }

    public Integer addAutomate(AutomateEntity automate) {
        String query = "INSERT INTO " + repoName + " VALUES " + automate.toQuery();
        Query nativeQuery = em.createNativeQuery(query, AutomateEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer updateAutomate(AutomateEntity automate) {
        String query = "UPDATE " + repoName +
                " SET name = " + automate.name +
                ",device_id_sens = " + automate.device_id_sens +
                ",sensor_id_sens = " + automate.sensor_id_sens +
                ",val = " + automate.val +
                ",device_id_acts = " + automate.device_id_acts +
                ",sensor_id_acts = " + automate.sensor_id_acts +
                ",set_state = " + automate.set_state +
                " WHERE id = " + automate.id;
        Query nativeQuery = em.createNativeQuery(query, AutomateEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer deleteAutomate(String name) {
        String query = "DELETE FROM " + repoName + " WHERE name = " + name;
        Query nativeQuery = em.createNativeQuery(query, AutomateEntity.class);
        return nativeQuery.executeUpdate();
    }
}
