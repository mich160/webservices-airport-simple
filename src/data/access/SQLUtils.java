package data.access;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils {
    public static Long extractCreatedID(ResultSet keyResultSet, String entityName) throws SQLException {
        if(keyResultSet.next()){
            return keyResultSet.getLong(1);
        }
        else {
            throw new SQLException(entityName+" creating failed!");
        }
    }
}
