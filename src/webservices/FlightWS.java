package webservices;

import data.entities.xml.xmlFlight;
import webservices.exceptions.DatabaseException;
import webservices.exceptions.NotLoggedException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService
@SOAPBinding
public interface FlightWS {

    @WebMethod
    List<String> getAllFromCities(@WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<String> getAllToCities(@WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<xmlFlight> getFlightsByDay(@WebParam(name = "date") String date,
                                    @WebParam(name = "authorizationToken") String authorizationToken) throws DatabaseException, NotLoggedException;

    @WebMethod
    List<xmlFlight> getFlightsFromToXDaysAheadAfterToday(@WebParam(name = "from") String from,
                                                      @WebParam(name = "to") String to,
                                                      @WebParam(name = "days") int days,
                                                      @WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    List<xmlFlight> getFlightsAfterTime(@WebParam(name = "from") String from,
                                     @WebParam(name = "to") String to,
                                     @WebParam(name = "time") String time,
                                     @WebParam(name = "authorizationToken") String authorizationToken) throws DatabaseException, NotLoggedException;

    @WebMethod
    List<String> getFlightClasses();

    @WebMethod
    long getFlightPrice(@WebParam(name = "flightID") long flightID,
                          @WebParam(name = "flightClass") String flightClass,
                        @WebParam(name = "authorizationToken") String authorizationToken) throws NotLoggedException, DatabaseException;

    @WebMethod
    xmlFlight getFlight(@WebParam(name = "flightID") long flightID,
                        @WebParam(name = "authorizationToken") String authorizationToken) throws DatabaseException, NotLoggedException;
}
