package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.tables.Device;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceRepository {
    private static final String url = "jdbc:mariadb://localhost:3306/home_auto";
    private static final String user = "root";
    private static final String password = "";
    private final Connection con;

    public DeviceRepository() throws SQLException {
        con = DriverManager.getConnection(url, user, password);
    }

    public List<Device> getAllDevices() {
        String query = "SELECT * FROM devices";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            List<Device> results = new ArrayList<>();
            while (rs.next()) {
                Device dv = new Device();
                dv.id = rs.getString("id");
                dv.name = rs.getString("name");
                dv.location = rs.getString("location");
                results.add(dv);
            }
            return results;
        } catch (SQLException e) {
            return null;
        }
    }

    public Device getDevice(int id) {
        String query = "SELECT * FROM devices WHERE id = " + id;
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();

            Device dv = new Device();
            dv.id = rs.getString("id");
            dv.name = rs.getString("name");
            dv.location = rs.getString("location");
            dv.sensor_nb = rs.getInt("sensor_nb");
            return dv;
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer addDevice(Device dv) {
        String update = String.format("INSERT INTO devices VALUES ('%s', '%s', '%s', '%s')", dv.id, dv.name, dv.location, dv.sensor_nb);
        try (Statement stmt = con.createStatement()) {
            return stmt.executeUpdate(update);
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer removeDevice(String id) {
        String update = String.format("DELETE FROM devices WHERE id = '%s'", id);
        try (Statement stmt = con.createStatement()) {
            return stmt.executeUpdate(update);
        } catch (SQLException e) {
            return null;
        }
    }
}
