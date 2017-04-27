package data.access;

import data.entities.Flight;
import data.sqliteUtils.DateTimeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightService implements Service<Long, Flight> {
    private final static String GET_ALL_SQL = "SELECT * FROM FLIGHT";
    private final static String GET_BY_ID_SQL = "SELECT * FROM FLIGHT WHERE ID = ?";
    private final static String GET_BY_DATE_SQL = "SELECT * FROM FLIGHT WHERE strftime(\"%Y-%m-%d\", FLIGHT.START_DATETIME) = ?";
    private final static String GET_AFTER_TIME_SQL = "SELECT * FROM FLIGHT WHERE FLIGHT.START_DATETIME > ? AND FLIGHT.FROM_WHERE = ? AND FLIGHT.TO_WHERE = ?";
    private final static String GET_X_DAYS_FROM_NOW_SQL = "SELECT * FROM FLIGHT WHERE (FLIGHT.START_DATETIME BETWEEN datetime(\"now\") AND ?) AND FLIGHT.FROM_WHERE = ? AND FLIGHT.TO_WHERE = ?";
    private final static String DELETE_SQL = "DELETE FROM FLIGHT WHERE ID = ?";
    private final static String UPDATE_SQL = "UPDATE FLIGHT SET START_DATETIME = ?, FROM_WHERE = ?, TO_WHERE = ?, SEATS_COUNT = ?, BASE_PRICE = ? WHERE ID = ?";
    private final static String SAVE_SQL = "INSERT INTO FLIGHT VALUES(NULL, ?, ?, ?, ?, ?)";
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
            Flight flight = extractFlightData(resultSet);
            result.add(flight);
        }

        return result;
    }

    public List<Flight> getByDate(LocalDate localDate) throws SQLException {
        PreparedStatement getByDateStatement = connection.prepareStatement(GET_BY_DATE_SQL);
        getByDateStatement.setString(1, DateTimeUtils.JavaDateToDatabaseDate(localDate));
        ResultSet resultSet = getByDateStatement.executeQuery();

        List<Flight> result = new ArrayList<>();
        while (resultSet.next()) {
            Flight flight = extractFlightData(resultSet);
            result.add(flight);
        }
        return result;
    }

    public List<Flight> getAfterTime(String from, String to, LocalDateTime localDateTime) throws SQLException {
        PreparedStatement getAfterDateTimeStatement = connection.prepareStatement(GET_AFTER_TIME_SQL);
        getAfterDateTimeStatement.setString(1, DateTimeUtils.JavaDateTimeToDatabaseDateTime(localDateTime));
        getAfterDateTimeStatement.setString(2, from);
        getAfterDateTimeStatement.setString(3, to);
        ResultSet resultSet = getAfterDateTimeStatement.executeQuery();

        List<Flight> result = new ArrayList<>();
        while (resultSet.next()) {
            Flight flight = extractFlightData(resultSet);
            result.add(flight);
        }
        return result;
    }

    public List<Flight> getXDaysAfterNow(String from, String to, int days) throws SQLException {
        PreparedStatement getXDaysAfterNowStatement = connection.prepareStatement(GET_X_DAYS_FROM_NOW_SQL);
        getXDaysAfterNowStatement.setString(1, DateTimeUtils.JavaDateTimeToDatabaseDateTime(LocalDateTime.now().plusDays(days)));
        getXDaysAfterNowStatement.setString(2, from);
        getXDaysAfterNowStatement.setString(3, to);
        ResultSet resultSet = getXDaysAfterNowStatement.executeQuery();

        List<Flight> result = new ArrayList<>();
        while (resultSet.next()) {
            Flight flight = extractFlightData(resultSet);
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
            result = extractFlightData(resultSet);
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
        updateStatement.setLong(6, entity.getBasePrice());
        updateStatement.executeUpdate();
    }

    @Override
    public Long save(Flight entity) throws SQLException {
        PreparedStatement saveStatement = connection.prepareStatement(SAVE_SQL);
        saveStatement.setString(1, DateTimeUtils.JavaDateTimeToDatabaseDateTime(entity.getStartDateTime()));
        saveStatement.setString(2, entity.getFromWhere());
        saveStatement.setString(3, entity.getToWhere());
        saveStatement.setInt(4, entity.getSeatsCount());
        saveStatement.setLong(5, entity.getBasePrice());
        saveStatement.executeUpdate();
        return SQLUtils.extractCreatedID(saveStatement.getGeneratedKeys(), "Flight");
    }

    public List<Integer> getSeatsTaken(long flightID) throws SQLException {
        PreparedStatement getSeatsTakenStatement = connection.prepareStatement(GET_SEATS_TAKEN_SQL);
        getSeatsTakenStatement.setLong(1, flightID);
        ResultSet resultSet = getSeatsTakenStatement.executeQuery();

        List<Integer> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(resultSet.getInt(1));
        }
        return result;
    }

    private Flight extractFlightData(ResultSet resultSet) throws SQLException {
        Flight result = new Flight();
        result.setID(resultSet.getLong(1))
                .setStartDateTime(DateTimeUtils.databaseDateTimeToJavaDateTime(resultSet.getString(2)))
                .setFromWhere(resultSet.getString(3))
                .setToWhere(resultSet.getString(4))
                .setSeatsCount(resultSet.getInt(5))
                .setBasePrice(resultSet.getLong(6));
        return result;
    }
}
