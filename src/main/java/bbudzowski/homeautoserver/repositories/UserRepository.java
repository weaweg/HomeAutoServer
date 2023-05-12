package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.SensorEntity;
import bbudzowski.homeautoserver.tables.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @PersistenceContext
    EntityManager em;
    String repoName = "users";

    public UserEntity getUser(String username) {
        String query = "SELECT * FROM " + repoName + " WHERE username = " + username;
        Query nativeQuery = em.createNativeQuery(query, UserEntity.class);
        return (UserEntity) nativeQuery.getSingleResult();
    }

    public Integer addUser(UserEntity user) {
        String query = "INSERT INTO " + repoName + " VALUES " + user.toQuery();
        Query nativeQuery = em.createNativeQuery(query, UserEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer updateUser(UserEntity user) {
        String query = "UPDATE " + repoName + " SET password = " + user.password + " WHERE username = " + user.username;
        Query nativeQuery = em.createNativeQuery(query, UserEntity.class);
        return nativeQuery.executeUpdate();
    }

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public Integer deleteUser(String username) {
        String query = "DELETE FROM " + repoName + " WHERE username = " + username;
        Query nativeQuery = em.createNativeQuery(query, SensorEntity.class);
        return nativeQuery.executeUpdate();
    }
}
