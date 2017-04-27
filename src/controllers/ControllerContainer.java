package controllers;

import data.DataServiceContainer;
import webservices.exceptions.DatabaseException;

import java.sql.SQLException;

public class ControllerContainer {
    private static ControllerContainer controllerContainer;

    public static ControllerContainer getControllerContainer() throws DatabaseException {
        if (controllerContainer == null) {
            controllerContainer = new ControllerContainer();
        }
        return controllerContainer;
    }

    private DataServiceContainer dataServiceContainer;
    private AuthController authController;
    private FlightController flightController;
    private TicketController ticketController;

    private ControllerContainer() throws DatabaseException {
        try {
            this.dataServiceContainer = DataServiceContainer.getDataServiceContainer();
            this.authController = new AuthController(dataServiceContainer);
            this.flightController = new FlightController(dataServiceContainer);
            this.ticketController = new TicketController(dataServiceContainer);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public AuthController getAuthController() {
        return authController;
    }

    public FlightController getFlightController() {
        return flightController;
    }

    public TicketController getTicketController() {
        return ticketController;
    }
}
