package data.access;

import data.ConnectionFactory;
import data.entities.Flight;
import data.sqliteUtils.DateTimeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightService implements Service<Long, Flight> {
    private final static String GET_ALL_SQL = "SELECT * FROM FLIGHT";
    private final static String GET_BY_ID_SQL = "SELECT * FROM FLIGHT WHERE ID = ?";
    private final static String DELETE_SQL = "DELETE FROM FLIGHT WHERE ID = ?";
    private final static String UPDATE_SQL = "UPDATE FLIGHT SET START_DATETIME = ?, FROM_WHERE = ?, TO_WHERE = ?, SEATS_COUNT = ? WHERE ID = ?";
    private final static String SAVE_SQL = "INSERT INTO FLIGHT VALUES(NULL, ?, ?, ?, ?)";
    private final static String GET_SEATS_TAKEN_SQL = "SELECT TICKET.SEAT FROM FLIGHT, TICKET WHERE FLIGHT.ID = TICKET.ID AND FLIGHT.ID = ?";

    private Connection connection;

    public FlightService(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public List<Flight> getAll() throws SQLException {
        PreparedStatement getAllStatement = connection.prepareStatement(GET_ALL_SQL);
        ResultSet resultSet = getAllStatement.executeQuery();

        List<Flight> result = new ArrayList<>();
        while (resultSet.next()) {
            Flight flight = new Flight();
            flight.setID(resultSet.getLong(1))
                    .setStartDateTime(DateTimeUtils.databaseDateTimeToJavaDateTime(resultSet.getString(2)))
                    .setFromWhere(resultSet.getString(3))
                    .setToWhere(resultSet.getString(4))
                    .setSeatsCount(resultSet.getInt(5));
            result.add(flight);
        }

        return result;
    }

    @Override
    public Flight getByID(Long id) throws SQLException {
        PreparedStatement getByIDStatement = connection.prepareStatement(GET_BY_ID_SQL);
        getByIDStatement.setLong(1, id);
        ResultSet resultSet = getByIDStatement.executeQuery();

        Flight result = null;
        if (resultSet.next()) {
            result = new Flight();
            result.setID(resultSet.getLong(1))
                    .setStartDateTime(DateTimeUtils.databaseDateTimeToJavaDateTime(resultSet.getString(2)))
                    .setFromWhere(resultSet.getString(3))
                    .setToWhere(resultSet.getString(4))
                    .setSeatsCount(resultSet.getInt(5));
        }
        return result;
    }

    @Override
    public void delete(Long id) throws SQLException {
        PreparedStatement deleteStatement = connection.prepareStatement(DELETE_SQL);
        deleteStatement.setLong(1, id);
        deleteStatement.executeUpdate();
    }

    @Override
    public void update(Flight entity) throws SQLException {
        PreparedStatement updateStatement = connection.prepareStatement(UPDATE_SQL);
        updateStatement.setString(1, DateTimeUtils.JavaDateTimeToDatabaseDateTime(entity.getStartDateTime()));
        updateStatement.setString(2, entity.getFromWhere());
        updateStatement.setString(3, entity.getToWhere());
        updateStatement.setInt(4, entity.getSeatsCount());
        updateStatement.setLong(5, entity.getID());
        updateStatement.executeUpdate();
    }

    @Override
    public Long save(Flight entity) throws SQLException {
        PreparedStatement saveStatement = connection.prepareStatement(SAVE_SQL);
        saveStatement.setString(1, DateTimeUtils.JavaDateTimeToDatabaseDateTime(entity.getStartDateTime()));
        saveStatement.setString(2, entity.getFromWhere());
        saveStatement.setString(3, entity.getToWhere());
        saveStatement.setInt(4, entity.getSeatsCount());
        saveStatement.executeUpdate();
        return SQLUtils.extractCreatedID(saveStatement.getGeneratedKeys(), "Flight");
    }

    public List<Integer> getSeatsTaken(long flightID) throws SQLException{
        PreparedStatement getSeatsTakenStatement = connection.prepareStatement(GET_SEATS_TAKEN_SQL);
        getSeatsTakenStatement.setLong(1,flightID);
        ResultSet resultSet = getSeatsTakenStatement.executeQuery();

        List<Integer> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(resultSet.getInt(1));
        }
        return result;
    }
}
