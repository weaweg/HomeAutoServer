package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.RepositoryConnection;
import bbudzowski.homeautoserver.tables.Measurement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MeasurementsRepository {
    private final RepositoryConnection db = new RepositoryConnection();
    private Measurement returnMeasurement(ResultSet rs) throws SQLException {
        Measurement measurement = new Measurement();
        measurement.device_id = rs.getString("device_id");
        measurement.sensor_id = rs.getInt("sensor_id");
        measurement.m_time = rs.getLong("m_time");
        measurement.val = rs.getFloat("val");
        return measurement;
    }

    public List<Measurement> getAllMeasurementsForSensor(String device_id, Integer sensor_id) {
        String query = String.format(
                "SELECT * FROM measurements WHERE device_id = '%s' AND sensor_id = '%s'" , device_id, sensor_id);
        try (ResultSet rs = db.selectQuery(query)){
            List<Measurement> results = new ArrayList<>();
            while (rs.next())
                results.add(returnMeasurement(rs));
            return results;
        } catch (Exception e) {
            return null;
        }
    }

    public Integer addMeasurement(Measurement ms) {
        String query = "INSERT INTO measurements VALUES " + ms.toQuery();
        return db.updateQuery(query);
    }
}
