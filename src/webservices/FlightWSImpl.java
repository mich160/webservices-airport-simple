package webservices;

import controllers.ControllerContainer;
import data.entities.Flight;
import data.entities.xml.xmlFlight;
import data.sqliteUtils.DateTimeUtils;
import webservices.exceptions.DatabaseException;
import webservices.exceptions.NotLoggedException;

import javax.annotation.PostConstruct;
import javax.jws.HandlerChain;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebService(endpointInterface = "webservices.FlightWS")
@HandlerChain(file = "handler-chain.xml")
public class FlightWSImpl implements FlightWS{
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
    public List<String> getAllFromCities(@WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(authorizationToken)){
                   return controllerContainer.getFlightController().getAllFromCities();
            }
            else{
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public List<String> getAllToCities(@WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(authorizationToken)){
                return controllerContainer.getFlightController().getAllToCities();
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
    public List<xmlFlight> getFlightsByDay(@WebParam(name = "date") String date,
                                           @WebParam(name = "authorizationToken") String authorizationToken) throws DatabaseException, NotLoggedException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(authorizationToken)){
                return controllerContainer.getFlightController().getFlightsByDay(DateTimeUtils.stringDateToJavaDate(date)).stream().map(xmlFlight::new).collect(Collectors.toList());
            }
            else{
                throw new NotLoggedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public List<xmlFlight> getFlightsFromToXDaysAheadAfterToday(@WebParam(name = "from") String from,
                                                                @WebParam(name = "to") String to,
                                                                @WebParam(name = "days") int days,
                                                                @WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(authorizationToken)){
                return controllerContainer.getFlightController().getFlightsFromToXDaysAheadAfterToday(from,to, days).stream().map(xmlFlight::new).collect(Collectors.toList());
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
    public List<xmlFlight> getFlightsAfterTime(@WebParam(name = "from") String from,
                                               @WebParam(name = "to") String to,
                                               @WebParam(name = "time") String time,
                                               @WebParam(name = "authorizationToken") String authorizationToken) throws DatabaseException, NotLoggedException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(authorizationToken)){
                return controllerContainer.getFlightController().getFlightsAfterTime(from, to, DateTimeUtils.stringDateTimeToJavaDateTime(time)).stream().map(xmlFlight::new).collect(Collectors.toList());
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
    public List<String> getFlightClasses() {
        return controllerContainer.getTicketController().getFlightClasses();
    }

    @Override
    public long getFlightPrice(@WebParam(name = "flightID") long flightID,
                               @WebParam(name = "flightClass") String flightClass,
                               @WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(authorizationToken)){
                return controllerContainer.getFlightController().getFlightPrice(flightID,flightClass);
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
    public xmlFlight getFlight(@WebParam(name = "flightID") long flightID,
                               @WebParam(name = "authorizationToken") String authorizationToken) throws DatabaseException, NotLoggedException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(authorizationToken)){
                return new xmlFlight(controllerContainer.getFlightController().getFlight(flightID));
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
