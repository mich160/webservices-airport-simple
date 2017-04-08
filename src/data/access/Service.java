package data.access;

import java.sql.SQLException;
import java.util.List;

public interface Service<I,E> {
    List<E> getAll() throws SQLException;
    E getByID(I id) throws SQLException;
    void delete(I id) throws SQLException;
    void update(E entity) throws SQLException;
    void save(E entity) throws SQLException;
}
