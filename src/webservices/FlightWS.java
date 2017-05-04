package webservices;

import data.entities.Flight;
import webservices.exceptions.DatabaseException;
import webservices.exceptions.NotLoggedException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@WebService
@SOAPBinding
public interface FlightWS {

    @WebMethod
    List<String> getAllFromCities(@WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<String> getAllToCities(@WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<Flight> getFlightsByDay(@WebParam(name = "date") XMLGregorianCalendar date,
                                 @WebParam(name = "authorizationToken") String authorizationToken) throws DatabaseException, NotLoggedException;

    @WebMethod
    List<Flight> getFlightsFromToXDaysAheadAfterToday(@WebParam(name = "from") String from,
                                                      @WebParam(name = "to") String to,
                                                      @WebParam(name = "days") int days,
                                                      @WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<Flight> getFlightsAfterTime(@WebParam(name = "from") String from,
                                     @WebParam(name = "to") String to,
                                     @WebParam(name = "time") XMLGregorianCalendar time,
                                     @WebParam(name = "authorizationToken") String authorizationToken) throws DatabaseException, NotLoggedException;
}
