package webservices;

import data.entities.Ticket;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "webservices.TicketWS")
public class TicketWSImpl implements TicketWS {//TODO, potem handler i client
    @Override
    public boolean isTicketAvailable(long flightID, String sessionToken) {
        return false;
    }

    @Override
    public List<Integer> getSeatsTaken(long flightID, String sessionToken) {
        return null;
    }

    @Override
    public int getAvailableSeatsCount(long flightID, String sessionToken) {
        return 0;
    }

    @Override
    public void bookTicket(long userID, long flightID, int seat, String flightClass, String sessionToken) {

    }

    @Override
    public void cancelTicket(long ticketID, String sessionToken) {

    }

    @Override
    public List<Ticket> getTicketsForFlight(long flightID, String sessionToken) {
        return null;
    }

    @Override
    public List<Ticket> getTicketsForClient(long userID, String sessionToken) {
        return null;
    }

    @Override
    public List<Ticket> getTicketsForUserAndFlight(long flightID, long clientID, String sessionToken) {
        return null;
    }

    @Override
    public byte[] getTicketConfirmation(long ticketID, String sessionToken) {
        return new byte[0];
    }
}
