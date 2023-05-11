package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends RepositoryConnection {

    private User returnUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.id = rs.getInt("id");
        user.username = rs.getString("username");
        user.password = rs.getString("password");
        return user;
    }

    public User getUser(String username) {
        String query = String.format("SELECT * FROM users WHERE username = '%s'", username);
        try (ResultSet rs = selectQuery(query)) {
            rs.next();
            return returnUser(rs);
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer addUser(User user) {
        String query = "INSERT INTO users VALUES " + user.toQuery();
        return updateQuery(query);
    }

    public Integer changePassword(String username, String password) {
        String query = String.format("UPDATE users SET password = '%s' WHERE username = '%s'", password, username);
        return updateQuery(query);
    }
}
