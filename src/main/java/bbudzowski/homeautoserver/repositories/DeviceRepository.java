package bbudzowski.homeautoserver.repositories;
import bbudzowski.homeautoserver.tables.Device;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DeviceRepository extends RepositoryConnection {

    private Device returnDevice(ResultSet rs) throws SQLException {
        Device device = new Device();
        device.device_id = rs.getString("device_id");
        device.ip_address = rs.getString("ip_address");
        device.name = rs.getString("name");
        device.location = rs.getString("location");
        return device;
    }

    public List<Device> getAllDevices() {
        String query = "SELECT * FROM devices";
        try (ResultSet rs = selectQuery(query)) {
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
        try (ResultSet rs = selectQuery(query)) {
            rs.next();
            return returnDevice(rs);
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer addDevice(Device device) {
        String query = "INSERT INTO devices VALUES " + device.toQuery();
        return updateQuery(query);
    }

    public Integer removeDevice(String id) {
        String query = String.format("DELETE FROM devices WHERE id = '%s'", id);
        return updateQuery(query);
    }

    public Integer updateDevicesIP(String id, String ip) {
        String query = String.format("UPDATE devices SET ip = '%s' WHERE id = '%s'", ip, id);
        return updateQuery(query);
    }
}
