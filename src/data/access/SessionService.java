package data.access;

import data.ConnectionFactory;
import data.entities.Session;
import data.sqliteUtils.DateTimeUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionService implements Service<Long, Session> {
    private final static String GET_ALL_SQL = "SELECT * FROM SESSION";
    private final static String GET_BY_ID_SQL = "SELECT * FROM SESSION WHERE ID = ?";
    private final static String GET_BY_TOKEN_SQL = "SELECT * FROM SESSION WHERE TOKEN = ?";
    private final static String GET_BY_USER_ID_SQL = "SELECT * FROM SESSION WHERE USER_ID = ?";
    private final static String DELETE_EXPIRED_SESSIONS_SQL = "DELETE FROM SESSION WHERE SESSION.EXPIRATION_DATE < datetime(\"now\")";
    private final static String DELETE_SQL = "DELETE FROM SESSION WHERE ID = ?";
    private final static String UPDATE_SQL = "UPDATE SESSION SET TOKEN = ?, USER_ID = ?, EXPIRATION_DATE = ? WHERE ID = ?";
    private final static String SAVE_SQL = "INSERT INTO SESSION VALUES(NULL, ?, ?, ?)";

    private Connection connection;

    public SessionService(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public List<Session> getAll() throws SQLException {
        PreparedStatement getAllStatement = connection.prepareStatement(GET_ALL_SQL);
        ResultSet resultSet = getAllStatement.executeQuery();

        List<Session> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(extractSessionData(resultSet));
        }
        return result;
    }

    @Override
    public Session getByID(Long id) throws SQLException {
        return getSessionByID(id, GET_BY_ID_SQL);
    }

    public Session getByToken(String token) throws SQLException {
        PreparedStatement getByTokenStatement = connection.prepareStatement(GET_BY_TOKEN_SQL);
        getByTokenStatement.setString(1, token);
        ResultSet resultSet = getByTokenStatement.executeQuery();

        Session result = null;
        if (resultSet.next()) {
            result = extractSessionData(resultSet);
        }
        return result;
    }

    public Session getByUserID(Long userID) throws SQLException {
        return getSessionByID(userID, GET_BY_USER_ID_SQL);
    }

    @Override
    public void delete(Long id) throws SQLException {
        PreparedStatement deleteStatement = connection.prepareStatement(DELETE_SQL);
        deleteStatement.setLong(1, id);
        deleteStatement.executeUpdate();
    }

    public void deleteExpiredSessions() throws SQLException{
        PreparedStatement deleteExpiredStatement = connection.prepareStatement(DELETE_EXPIRED_SESSIONS_SQL);
        deleteExpiredStatement.executeUpdate();
    }

    @Override
    public void update(Session entity) throws SQLException {
        PreparedStatement updateStatement = connection.prepareStatement(UPDATE_SQL);
        updateStatement.setString(1, entity.getToken());
        updateStatement.setLong(2, entity.getUserID());
        updateStatement.setString(3, DateTimeUtils.JavaDateTimeToDatabaseDateTime(entity.getExpirationDateTime()));
        updateStatement.setLong(4, entity.getID());
        updateStatement.executeUpdate();
    }

    @Override
    public Long save(Session entity) throws SQLException {
        PreparedStatement saveStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
        saveStatement.setString(1, entity.getToken());
        saveStatement.setLong(2, entity.getUserID());
        saveStatement.setString(3, DateTimeUtils.JavaDateTimeToDatabaseDateTime(entity.getExpirationDateTime()));
        saveStatement.executeUpdate();
        return SQLUtils.extractCreatedID(saveStatement.getGeneratedKeys(), "Session");
    }

    private Session extractSessionData(ResultSet resultSet) throws SQLException {
        Session session = new Session();
        session.setID(resultSet.getLong(1))
                .setToken(resultSet.getString(2))
                .setUserID(resultSet.getLong(3))
                .setExpirationDateTime(DateTimeUtils.databaseDateTimeToJavaDateTime(resultSet.getString(4)));
        return session;
    }

    private Session getSessionByID(Long key, String ID_SQL) throws SQLException {
        PreparedStatement getByIDStatement = connection.prepareStatement(ID_SQL);
        getByIDStatement.setLong(1, key);
        ResultSet resultSet = getByIDStatement.executeQuery();

        Session result = null;
        if (resultSet.next()) {
            result = extractSessionData(resultSet);
        }
        return result;
    }
}
