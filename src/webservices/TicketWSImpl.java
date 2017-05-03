package webservices;

import controllers.ControllerContainer;
import data.entities.Ticket;
import webservices.exceptions.*;

import javax.annotation.PostConstruct;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebService(endpointInterface = "webservices.TicketWS")
public class TicketWSImpl implements TicketWS {
    private ControllerContainer controllerContainer;

    @PostConstruct
    public void init(){
        try {
            controllerContainer = ControllerContainer.getControllerContainer();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isTicketAvailable(@WebParam(name = "flightID") long flightID,
                                     @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, DatabaseException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(sessionToken)){
                return controllerContainer.getTicketController().isTicketAvailable(flightID);
            }
            else {
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public List<Integer> getSeatsTaken(@WebParam(name = "flightID") long flightID,
                                       @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, DatabaseException {
        try {
            if (controllerContainer.getAuthController().isSessionValid(sessionToken)){
                return controllerContainer.getTicketController().getSeatsTaken(flightID);
            }
            else {
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public int getAvailableSeatsCount(@WebParam(name = "flightID") long flightID,
                                      @WebParam(name = "sessionToken") String sessionToken) throws DatabaseException, NotLoggedException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(sessionToken)){
                return controllerContainer.getTicketController().getAvailableSeatsCount(flightID);
            }
            else {
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public void bookTicket(@WebParam(name = "userID") long userID,
                           @WebParam(name = "flightID") long flightID,
                           @WebParam(name = "seat") int seat,
                           @WebParam(name = "flightClass") String flightClass,
                           @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, DatabaseException, InvalidBookingException, NoSuchFlightClassException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(sessionToken)){
                controllerContainer.getTicketController().bookTicket(userID, flightID, seat, flightClass);
            }
            else {
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public void cancelTicket(@WebParam(name = "ticketID") long ticketID,
                             @WebParam(name = "sessionToken") String sessionToken) throws CancelingNotLongerPossibleException, DatabaseException, NotLoggedException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(sessionToken)){
                controllerContainer.getTicketController().cancelTicket(ticketID);
            }
            else {
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public List<Ticket> getTicketsForFlight(@WebParam(name = "flightID") long flightID,
                                            @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, DatabaseException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(sessionToken)){
                return controllerContainer.getTicketController().getTicketsForFlight(flightID);
            }
            else {
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public List<Ticket> getTicketsForClient(@WebParam(name = "userID") long userID,
                                            @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, DatabaseException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(sessionToken)){
                return controllerContainer.getTicketController().getTicketsForUser(userID);
            }
            else {
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public List<Ticket> getTicketsForUserAndFlight(@WebParam(name = "flightID") long flightID,
                                                   @WebParam(name = "userID") long userID,
                                                   @WebParam(name = "sessionToken") String sessionToken) throws DatabaseException, NotLoggedException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(sessionToken)){
                return controllerContainer.getTicketController().getTicketsForUserAndFlight(flightID, userID);
            }
            else {
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public byte[] getTicketConfirmation(@WebParam(name = "ticketID") long ticketID,
                                        @WebParam(name = "sessionToken") String sessionToken) throws NotLoggedException, IOException, DatabaseException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(sessionToken)){
                return controllerContainer.getTicketController().getTicketConfirmation(ticketID);
            }
            else {
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }
}
