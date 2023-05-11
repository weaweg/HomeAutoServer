package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
public class User {
    @Id
    public Integer user_id;
    public String username;
    public String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toQuery() {
        return String.format("('%s', '%s', '%s')",
                this.user_id, this.username, this.password);
    }
}
