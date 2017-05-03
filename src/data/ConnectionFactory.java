package data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public final static String DB_PATH = "C:/fullrandom/uczelnia/moje/2st/rsi/soapProj/rsi-1/database.db";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                File dbFile = new File(DB_PATH);
                String connectionPath = "jdbc:sqlite:" + dbFile.getAbsolutePath().replace("\\","\\\\");
                connection = DriverManager.getConnection(connectionPath);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
