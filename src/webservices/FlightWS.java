package webservices;

import data.entities.Flight;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@WebService
@SOAPBinding
public interface FlightWS {
    @WebMethod
    List<String> getAllFromCities(String authorizationToken);

    @WebMethod
    List<String> getAllToCities(String authorizationToken);

    @WebMethod
    List<Flight> getFlightsByDay(XMLGregorianCalendar date, String authorizationToken);

    @WebMethod
    List<Flight> getFlightsFromToXDaysAheadAfterToday(String from, String to, int days, String authorizationToken);

    @WebMethod
    List<Flight> getFlightsAfterTime(String from, String to, XMLGregorianCalendar time, String authorizationToken);
}
