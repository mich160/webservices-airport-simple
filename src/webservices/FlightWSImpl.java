package webservices;

import controllers.ControllerContainer;
import data.entities.Flight;
import utils.CalendarUtils;
import webservices.exceptions.DatabaseException;
import webservices.exceptions.NotLoggedException;

import javax.annotation.PostConstruct;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.SQLException;
import java.util.List;

@WebService(endpointInterface = "webservices.FlightWS")
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
    public List<String> getAllFromCities(String authorizationToken) throws NotLoggedException, DatabaseException {
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
    public List<String> getAllToCities(String authorizationToken) throws NotLoggedException, DatabaseException {
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
    public List<Flight> getFlightsByDay(XMLGregorianCalendar date, String authorizationToken) throws DatabaseException, NotLoggedException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(authorizationToken)){
                return controllerContainer.getFlightController().getFlightsByDay(CalendarUtils.xmlGregorianCalendarToLocalDate(date));
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
    public List<Flight> getFlightsFromToXDaysAheadAfterToday(String from, String to, int days, String authorizationToken) throws NotLoggedException, DatabaseException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(authorizationToken)){
                return controllerContainer.getFlightController().getFlightsFromToXDaysAheadAfterToday(from,to, days);
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
    public List<Flight> getFlightsAfterTime(String from, String to, XMLGregorianCalendar time, String authorizationToken) throws DatabaseException, NotLoggedException {
        try {
            if(controllerContainer.getAuthController().isSessionValid(authorizationToken)){
                return controllerContainer.getFlightController().getFlightsAfterTime(from, to, CalendarUtils.xmlGregorianCalendarToLocalDateTime(time));
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
