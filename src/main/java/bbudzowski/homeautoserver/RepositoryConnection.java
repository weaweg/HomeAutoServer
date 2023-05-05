package bbudzowski.homeautoserver;

import java.sql.*;

public class RepositoryConnection {
    private static final String url = "jdbc:mariadb://localhost:3306/home_auto";
    private static final String user = "root";
    private static final String password = "";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public Integer updateQuery(String query) {
        try (Connection con = this.connect()) {
            Statement stmt = con.createStatement();
            Integer rows = stmt.executeUpdate(query);
            con.close();
            return rows;
        } catch (SQLException e) {
            return null;
        }
    }

    public ResultSet selectQuery(String query) {
        try (Connection con = this.connect()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            con.close();
            return rs;
        } catch (SQLException e) {
            return null;
        }
    }
}
