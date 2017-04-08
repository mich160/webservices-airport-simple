package data.access;

import data.entities.Flight;

import java.sql.SQLException;
import java.util.List;

public class FlightService implements Service<Long, Flight>{
    @Override
    public List<Flight> getAll() throws SQLException {
        return null;
    }

    @Override
    public Flight getByID(Long id) throws SQLException {
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException {

    }

    @Override
    public void update(Flight entity) throws SQLException {

    }

    @Override
    public void save(Flight entity) throws SQLException {

    }//todo
}
