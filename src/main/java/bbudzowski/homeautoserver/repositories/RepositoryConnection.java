package bbudzowski.homeautoserver.repositories;

import java.sql.*;

public class RepositoryConnection {
    protected static final String url = "jdbc:mariadb://localhost:3306/home_auto";
    protected static final String user = "automation";
    protected static final String password = "tial2o3";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public Integer updateQuery(String query) {
        try (Connection con = this.connect()) {
            Statement stmt = con.createStatement();
            int rows = stmt.executeUpdate(query);
            con.close();
            if (rows == 0) return null;
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
