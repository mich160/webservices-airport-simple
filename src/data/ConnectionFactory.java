package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static String DB_PATH = "database.db";

    public static Connection getConnection() throws SQLException {
        Connection result = null;

        result = DriverManager.getConnection(String.format("jdbc:sqlite:%s",DB_PATH));
        return result;
    }
}
