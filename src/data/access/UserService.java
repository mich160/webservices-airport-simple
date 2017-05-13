package data.access;

import data.entities.User;
import data.sqliteUtils.DateTimeUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Service<Long, User> {
    private final static String GET_ALL_SQL = "SELECT * FROM USER";
    private final static String GET_BY_ID_SQL = "SELECT * FROM USER WHERE ID = ?";
    private final static String GET_BY_LOGIN_SQL = "SELECT * FROM USER WHERE LOGIN = ?";
    private final static String DELETE_SQL = "DELETE FROM USER WHERE ID = ?";
    private final static String UPDATE_SQL = "UPDATE USER SET NAME = ?, SURNAME = ?, BIRTH_DATE = ?, PHONE_NUMBER = ?, LOGIN = ?, PASSWORD_HASH = ? WHERE ID = ?";
    private final static String SAVE_SQL = "INSERT INTO USER VALUES(NULL, ?, ?, ?, ?, ?, ?)";

    private Connection connection;

    public UserService(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public List<User> getAll() throws SQLException {
        PreparedStatement getAllStatement = connection.prepareStatement(GET_ALL_SQL);
        ResultSet resultSet = getAllStatement.executeQuery();

        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setID(resultSet.getLong(1))
                    .setName(resultSet.getString(2))
                    .setSurname(resultSet.getString(3))
                    .setDateOfBirth(DateTimeUtils.stringDateToJavaDate(resultSet.getString(4)))
                    .setPhoneNumber(resultSet.getLong(5))
                    .setLogin(resultSet.getString(6))
                    .setPasswordHash(resultSet.getString(7));
            result.add(user);
        }

        return result;
    }

    @Override
    public User getByID(Long id) throws SQLException {
        PreparedStatement getByIDStatement = connection.prepareStatement(GET_BY_ID_SQL);
        getByIDStatement.setLong(1, id);
        ResultSet resultSet = getByIDStatement.executeQuery();

        User result = null;
        if (resultSet.next()) {
            result = new User();
            result.setID(resultSet.getLong(1))
                    .setName(resultSet.getString(2))
                    .setSurname(resultSet.getString(3))
                    .setDateOfBirth(DateTimeUtils.stringDateToJavaDate(resultSet.getString(4)))
                    .setPhoneNumber(resultSet.getLong(5))
                    .setLogin(resultSet.getString(6))
                    .setPasswordHash(resultSet.getString(7));
        }
        return result;
    }

    public User getByLogin(String login) throws SQLException {
        PreparedStatement getByLoginStatement = connection.prepareStatement(GET_BY_LOGIN_SQL);
        getByLoginStatement.setString(1, login);
        ResultSet resultSet = getByLoginStatement.executeQuery();

        User result = null;
        if (resultSet.next()) {
            result = new User();
            result.setID(resultSet.getLong(1))
                    .setName(resultSet.getString(2))
                    .setSurname(resultSet.getString(3))
                    .setDateOfBirth(DateTimeUtils.stringDateToJavaDate(resultSet.getString(4)))
                    .setPhoneNumber(resultSet.getLong(5))
                    .setLogin(resultSet.getString(6))
                    .setPasswordHash(resultSet.getString(7));
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
    public void update(User entity) throws SQLException {
        PreparedStatement updateStatement = connection.prepareStatement(UPDATE_SQL);
        updateStatement.setString(1, entity.getName());
        updateStatement.setString(2, entity.getSurname());
        updateStatement.setString(3, DateTimeUtils.JavaDateToStringDate(entity.getDateOfBirth()));
        updateStatement.setLong(4, entity.getPhoneNumber());
        updateStatement.setString(5, entity.getLogin());
        updateStatement.setString(6, entity.getPasswordHash());
        updateStatement.setLong(7, entity.getID());
        updateStatement.executeUpdate();
    }

    @Override
    public Long save(User entity) throws SQLException {
        PreparedStatement saveStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
        saveStatement.setString(1, entity.getName());
        saveStatement.setString(2, entity.getSurname());
        saveStatement.setString(3, DateTimeUtils.JavaDateToStringDate(entity.getDateOfBirth()));
        saveStatement.setLong(4, entity.getPhoneNumber());
        saveStatement.setString(5, entity.getLogin());
        saveStatement.setString(6, entity.getPasswordHash());
        saveStatement.executeUpdate();
        return SQLUtils.extractCreatedID(saveStatement.getGeneratedKeys(), "User");
    }

}
