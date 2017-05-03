package webservices;

import data.entities.Ticket;
import webservices.exceptions.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.soap.MTOM;
import java.io.IOException;
import java.util.List;

@MTOM
@WebService
@SOAPBinding
public interface TicketWS {

    @WebMethod
    boolean isTicketAvailable(long flightID, String sessionToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<Integer> getSeatsTaken(long flightID, String sessionToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    int getAvailableSeatsCount(long flightID, String sessionToken) throws DatabaseException, NotLoggedException;

    @WebMethod
    void bookTicket(long userID, long flightID, int seat, String flightClass, String sessionToken) throws NotLoggedException, DatabaseException, InvalidBookingException, NoSuchFlightClassException;

    @WebMethod
    void cancelTicket(long ticketID, String sessionToken) throws CancelingNotLongerPossibleException, DatabaseException, NotLoggedException;

    @WebMethod
    List<Ticket> getTicketsForFlight(long flightID, String sessionToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<Ticket> getTicketsForClient(long userID, String sessionToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<Ticket> getTicketsForUserAndFlight(long flightID, long clientID, String sessionToken) throws DatabaseException, NotLoggedException;

    @WebMethod
    byte[] getTicketConfirmation(long ticketID, String sessionToken) throws NotLoggedException, IOException, DatabaseException;
}
