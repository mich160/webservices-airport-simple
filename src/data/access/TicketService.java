package data.access;

import data.entities.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketService implements Service<Long, Ticket> {
    private final static String GET_ALL_SQL = "SELECT * FROM TICKET";
    private final static String GET_BY_ID_SQL = "SELECT * FROM TICKET WHERE ID = ?";
    private final static String GET_BY_USER_ID = "SELECT * FROM TICKET WHERE USER_ID = ?";
    private final static String GET_BY_FLIGHT_ID = "SELECT * FROM TICKET WHERE FLIGHT_ID = ?";
    private final static String GET_BY_FLIGHT_AND_USER_ID = "SELECT * FROM TICKET WHERE USER_ID = ? AND FLIGHT_ID = ?";
    private final static String DELETE_SQL = "DELETE FROM TICKET WHERE ID = ?";
    private final static String UPDATE_SQL = "UPDATE TICKET SET FLIGHT_ID = ?, USER_ID = ?, PRICE = ?, CLASS = ?, SEAT = ? WHERE ID = ?";
    private final static String SAVE_SQL = "INSERT INTO TICKET VALUES(NULL, ?, ?, ?, ?, ?)";

    private Connection connection;

    public TicketService(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public List<Ticket> getAll() throws SQLException {
        PreparedStatement getAllStatement = connection.prepareStatement(GET_ALL_SQL);
        ResultSet resultSet = getAllStatement.executeQuery();

        List<Ticket> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(extractTicketData(resultSet));
        }
        return result;
    }

    @Override
    public Ticket getByID(Long id) throws SQLException {
        PreparedStatement getByIDStatement = connection.prepareStatement(GET_BY_ID_SQL);
        getByIDStatement.setLong(1, id);
        ResultSet resultSet = getByIDStatement.executeQuery();

        Ticket result = null;
        if (resultSet.next()) {
            result = extractTicketData(resultSet);
        }
        return result;
    }

    public List<Ticket> getByUserID(long userID) throws SQLException {
        PreparedStatement getByUserIDStatement = connection.prepareStatement(GET_BY_USER_ID);
        getByUserIDStatement.setLong(1, userID);
        ResultSet resultSet = getByUserIDStatement.executeQuery();

        List<Ticket> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(extractTicketData(resultSet));
        }
        return result;
    }

    public List<Ticket> getByFlightID(long flightID) throws SQLException {
        PreparedStatement getByFlightIDStatement = connection.prepareStatement(GET_BY_FLIGHT_ID);
        getByFlightIDStatement.setLong(1, flightID);
        ResultSet resultSet = getByFlightIDStatement.executeQuery();

        List<Ticket> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(extractTicketData(resultSet));
        }
        return result;
    }

    public List<Ticket> getByUserAndFlightID(long userID, long flightID) throws SQLException {
        PreparedStatement getByUserAndFlightIDStatement = connection.prepareStatement(GET_BY_FLIGHT_AND_USER_ID);
        getByUserAndFlightIDStatement.setLong(1, userID);
        getByUserAndFlightIDStatement.setLong(2, flightID);
        ResultSet resultSet = getByUserAndFlightIDStatement.executeQuery();

        List<Ticket> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(extractTicketData(resultSet));
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
    public void update(Ticket entity) throws SQLException {
        PreparedStatement updateStatement = connection.prepareStatement(UPDATE_SQL);
        updateStatement.setLong(1, entity.getFlightID());
        updateStatement.setLong(2, entity.getUserID());
        updateStatement.setLong(3, entity.getPrice());
        updateStatement.setString(4, entity.getFlightClass());
        updateStatement.setInt(5, entity.getSeat());
        updateStatement.setLong(6, entity.getID());
        updateStatement.executeUpdate();
    }

    @Override
    public Long save(Ticket entity) throws SQLException {
        PreparedStatement saveStatement = connection.prepareStatement(SAVE_SQL);
        saveStatement.setLong(1, entity.getFlightID());
        saveStatement.setLong(2, entity.getUserID());
        saveStatement.setLong(3, entity.getPrice());
        saveStatement.setString(4, entity.getFlightClass());
        saveStatement.setInt(5, entity.getSeat());
        saveStatement.executeUpdate();
        return SQLUtils.extractCreatedID(saveStatement.getGeneratedKeys(), "Ticket");
    }

    private Ticket extractTicketData(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setID(resultSet.getLong(1))
                .setFlightID(resultSet.getLong(2))
                .setUserID(resultSet.getLong(3))
                .setPrice(resultSet.getLong(4))
                .setFlightClass(resultSet.getString(5))
                .setSeat(resultSet.getInt(6));
        return ticket;
    }
}
