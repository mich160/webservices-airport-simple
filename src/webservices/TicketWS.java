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
    boolean isTicketAvailable(long flightID);

    @WebMethod
    List<Integer> getSeatsTaken(long flightID);

    @WebMethod
    int getAvailableSeatsCount(long flightID);

    @WebMethod
    void bookTicket(long clientID, long flightID);

    @WebMethod
    void cancelTicket(long ticketID);

    @WebMethod
    List<Ticket> getTicketsForFlight(long flightID);

    @WebMethod
    List<Ticket> getTicketsForClient(long clientID);

    @WebMethod
    List<Ticket> getTicketsForClientAndFlight(long flightID, long clientID);

    @WebMethod
    byte[] getTicketConfirmation(long ticketID);
}
