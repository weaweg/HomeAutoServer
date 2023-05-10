package bbudzowski.homeautoserver.repositories;
import bbudzowski.homeautoserver.RepositoryConnection;
import bbudzowski.homeautoserver.tables.Device;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeviceRepository {
    private final RepositoryConnection db = new RepositoryConnection();

    private Device returnDevice(ResultSet rs) throws SQLException {
        Device device = new Device();
        device.id = rs.getString("id");
        device.ip = rs.getString("ip");
        device.name = rs.getString("name");
        device.location = rs.getString("location");
        return device;
    }

    public List<Device> getAllDevices() {
        String query = "SELECT * FROM devices";
        try (ResultSet rs = db.selectQuery(query)) {
            List<Device> results = new ArrayList<>();
            while (rs.next())
                results.add(returnDevice(rs));
            return results;
        } catch (SQLException e) {
            return null;
        }
    }

    public Device getDevice(String id) {
        String query = String.format("SELECT * FROM devices WHERE id = '%s'", id);
        try (ResultSet rs = db.selectQuery(query)) {
            rs.next();
            return returnDevice(rs);
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer addDevice(Device device) {
        String query = "INSERT INTO devices VALUES " + device.toQuery();
        return db.updateQuery(query);
    }

    public Integer removeDevice(String id) {
        String query = String.format("DELETE FROM devices WHERE id = '%s'", id);
        return db.updateQuery(query);
    }

    public Integer updateDevicesIP(String id, String ip) {
        String query = String.format("UPDATE devices SET ip = '%s' WHERE id = '%s'", ip, id);
        return db.updateQuery(query);
    }
}
