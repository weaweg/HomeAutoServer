package bbudzowski.homeautoserver.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer user_id;
    public String username;
    public String password;

    public String toQuery() {
        return String.format("(''%s', '%s')",
                this.username, this.password);
    }
}
