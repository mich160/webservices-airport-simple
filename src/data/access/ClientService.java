package data.access;

import data.ConnectionFactory;
import data.entities.Client;
import data.sqliteUtils.DateTimeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService implements Service<Long, Client>{
    private static String GET_ALL_SQL = "SELECT * FROM CLIENT";
    private static String GET_BY_ID_SQL = "SELECT * FROM CLIENT WHERE ID = ?";
    private static String DELETE_SQL = "DELETE FROM CLIENT WHERE ID = ?";
    private static String UPDATE_SQL = "UPDATE CLIENT SET NAME = ?, SURNAME = ?, BIRTH_DATE = ?, PHONE_NUMBER = ? WHERE ID = ?";
    private static String SAVE_SQL = "INSERT INTO CLIENT VALUES(NULL, ?, ?, ?, ?)";

    private Connection connection;

    public ClientService() throws SQLException {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public List<Client> getAll() throws SQLException {
        PreparedStatement getAllStatement = connection.prepareStatement(GET_ALL_SQL);
        ResultSet resultSet = getAllStatement.executeQuery();

        List<Client> result = new ArrayList<>();
        while (resultSet.next()){
            Client client = new Client();
            client.setID(resultSet.getLong(1))
                    .setName(resultSet.getString(2))
                    .setSurname(resultSet.getString(3))
                    .setDateOfBirth(DateTimeUtils.databaseDateToJavaDate(resultSet.getString(4)))
                    .setPhoneNumber(resultSet.getLong(5));
            result.add(client);
        }

        return result;
    }

    @Override
    public Client getByID(Long id) throws SQLException {
        PreparedStatement getByIDStatement = connection.prepareStatement(GET_BY_ID_SQL);
        getByIDStatement.setLong(1,id);
        ResultSet resultSet = getByIDStatement.executeQuery();

        Client result = null;
        if (resultSet.next()){
            result = new Client();
            result.setID(resultSet.getLong(1))
                    .setName(resultSet.getString(2))
                    .setSurname(resultSet.getString(3))
                    .setDateOfBirth(DateTimeUtils.databaseDateToJavaDate(resultSet.getString(4)))
                    .setPhoneNumber(resultSet.getLong(5));
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
    public void update(Client entity) throws SQLException {
        PreparedStatement updateStatement = connection.prepareStatement(UPDATE_SQL);
        updateStatement.setString(1, entity.getName());
        updateStatement.setString(2, entity.getSurname());
        updateStatement.setString(3, DateTimeUtils.JavaDateToDatabaseDate(entity.getDateOfBirth()));
        updateStatement.setLong(4, entity.getPhoneNumber());
        updateStatement.setLong(5, entity.getID());
        updateStatement.executeUpdate();
    }

    @Override
    public void save(Client entity) throws SQLException {
        PreparedStatement saveStatement = connection.prepareStatement(SAVE_SQL);
        saveStatement.setString(1, entity.getName());
        saveStatement.setString(2, entity.getSurname());
        saveStatement.setString(3, DateTimeUtils.JavaDateToDatabaseDate(entity.getDateOfBirth()));
        saveStatement.setLong(4, entity.getPhoneNumber());
        saveStatement.executeUpdate();
    }

}
