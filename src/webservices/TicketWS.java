package webservices;

import data.entities.Ticket;
import webservices.exceptions.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
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
    boolean isTicketAvailable(@WebParam(name = "flightID") long flightID,
                              @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<Integer> getSeatsTaken(@WebParam(name = "flightID") long flightID,
                                @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    int getAvailableSeatsCount(@WebParam(name = "flightID") long flightID,
                               @WebParam(name = "sessionToken") String sessionToken) throws DatabaseException, NotLoggedException;

    @WebMethod
    void bookTicket(@WebParam(name = "userID") long userID,
                    @WebParam(name = "flightID") long flightID,
                    @WebParam(name = "seat") int seat,
                    @WebParam(name = "flightClass") String flightClass,
                    @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, DatabaseException, InvalidBookingException, NoSuchFlightClassException;

    @WebMethod
    void cancelTicket(@WebParam(name = "ticketID") long ticketID,
                      @WebParam(name = "sessionToken") String sessionToken) throws CancelingNotLongerPossibleException, DatabaseException, NotLoggedException;

    @WebMethod
    List<Ticket> getTicketsForFlight(@WebParam(name = "flightID") long flightID,
                                     @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<Ticket> getTicketsForClient(@WebParam(name = "userID") long userID,
                                     @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<Ticket> getTicketsForUserAndFlight(@WebParam(name = "flightID") long flightID,
                                            @WebParam(name = "userID") long userID,
                                            @WebParam(name = "sessionToken") String sessionToken) throws DatabaseException, NotLoggedException;

    @WebMethod
    byte[] getTicketConfirmation(@WebParam(name = "ticketID") long ticketID,
                                 @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, IOException, DatabaseException;
}
