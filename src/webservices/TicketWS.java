package webservices;

import data.entities.Ticket;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.soap.MTOM;
import java.util.List;

@MTOM
@WebService
@SOAPBinding
public interface TicketWS {

    @WebMethod
    boolean isTicketAvailable(long flightID, String sessionToken);

    @WebMethod
    List<Integer> getSeatsTaken(long flightID, String sessionToken);

    @WebMethod
    int getAvailableSeatsCount(long flightID, String sessionToken);

    @WebMethod
    void bookTicket(long userID, long flightID, int seat, String flightClass, String sessionToken);

    @WebMethod
    void cancelTicket(long ticketID, String sessionToken);

    @WebMethod
    List<Ticket> getTicketsForFlight(long flightID, String sessionToken);

    @WebMethod
    List<Ticket> getTicketsForClient(long userID, String sessionToken);

    @WebMethod
    List<Ticket> getTicketsForUserAndFlight(long flightID, long clientID, String sessionToken);

    @WebMethod
    byte[] getTicketConfirmation(long ticketID, String sessionToken);
}
