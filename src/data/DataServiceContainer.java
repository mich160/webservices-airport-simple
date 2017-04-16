package data;

import data.access.FlightService;
import data.access.SessionService;
import data.access.TicketService;
import data.access.UserService;
import data.constants.CitiesService;
import data.constants.FlightClassesService;

import java.sql.Connection;
import java.sql.SQLException;

public class DataServiceContainer {
    private static DataServiceContainer dataServiceContainer;

    public static DataServiceContainer getDataServiceContainer() throws SQLException {
        if(dataServiceContainer == null){
            dataServiceContainer = new DataServiceContainer();
        }
        return dataServiceContainer;
    }

    private Connection connection;
    private FlightService flightService;
    private TicketService ticketService;
    private UserService userService;
    private SessionService sessionService;
    private CitiesService citiesService;
    private FlightClassesService classesService;

    private DataServiceContainer() throws SQLException {
        connection = ConnectionFactory.getConnection();
        flightService = new FlightService(connection);
        ticketService = new TicketService(connection);
        userService = new UserService(connection);
        sessionService = new SessionService(connection);
    }

    public FlightService getFlightService() {
        return flightService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public UserService getUserService() {
        return userService;
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public CitiesService getCitiesService() {
        return citiesService;
    }

    public FlightClassesService getClassesService() {
        return classesService;
    }
}
