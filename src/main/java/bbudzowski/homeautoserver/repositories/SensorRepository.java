package bbudzowski.homeautoserver.repositories;

import bbudzowski.homeautoserver.RepositoryConnection;
import bbudzowski.homeautoserver.tables.Sensor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SensorRepository {
    private final RepositoryConnection db = new RepositoryConnection();

    private Sensor returnSensor(ResultSet rs) throws SQLException {
        Sensor sens = new Sensor();
        sens.id = rs.getInt("id");
        sens.device_id = rs.getString("device_id");
        sens.data_type = rs.getInt("data_type");
        sens.current_state = rs.getInt("current_state");
        if(rs.wasNull()) sens.current_state = null;
        sens.units = rs.getString("units");
        sens.period = rs.getInt("period");
        return sens;
    }

    public List<Sensor> getAllSensors() {
        String query = "SELECT * FROM sensors";
        ResultSet rs = db.selectQuery(query);
        try {
            List<Sensor> results = new ArrayList<>();
            while (rs.next())
                results.add(returnSensor(rs));
            return results;
        } catch (SQLException e) {
            return null;
        }
    }

    public Sensor getSensor(String device_id, Integer id) {
        String query = String.format("SELECT * FROM sensors WHERE device_id = '%s' AND id = '%s'", device_id, id);
        ResultSet rs = db.selectQuery(query);
        try {
            rs.next();
            return returnSensor(rs);
        }
        catch (SQLException e) {
            return null;
        }
    }

    public Integer addSensor(Sensor sens) {
        if(sens.current_state == null) sens.current_state = 0;
        String query = "INSERT INTO sensors VALUES " + sens.toQuery();
        return db.updateQuery(query);
    }

    public Integer removeSensor(String device_id, Integer id) {
        String query = String.format("DELETE FROM sensors WHERE id = '%s'", id);
        return db.updateQuery(query);
    }
}
