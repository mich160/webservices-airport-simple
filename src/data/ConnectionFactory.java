package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public final static String DB_PATH = "database.db";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", DB_PATH));
        }
        return connection;
    }
}
