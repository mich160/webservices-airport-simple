package data.access;

import data.entities.Ticket;

import java.sql.SQLException;
import java.util.List;

public class TicketService implements Service<Long, Ticket>{
    @Override
    public List<Ticket> getAll() throws SQLException {
        return null;
    }

    @Override
    public Ticket getByID(Long id) throws SQLException {
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException {

    }

    @Override
    public void update(Ticket entity) throws SQLException {

    }

    @Override
    public void save(Ticket entity) throws SQLException {

    }//todo
}
