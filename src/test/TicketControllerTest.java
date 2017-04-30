package test;

import auth.exceptions.CancelingNotLongerPossibleException;
import auth.exceptions.InvalidBookingException;
import auth.exceptions.NoSuchFlightClassException;
import controllers.ControllerContainer;
import data.DataServiceContainer;
import data.entities.Flight;
import data.entities.Ticket;
import data.entities.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import webservices.exceptions.DatabaseException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TicketControllerTest {
    public static DataServiceContainer serviceContainer;
    public static ControllerContainer controllerContainer;
    public static long userID;
    public static long flightID;

    @BeforeAll
    public static void initAll(){
        try {
            serviceContainer = DataServiceContainer.getDataServiceContainer();
            controllerContainer = ControllerContainer.getControllerContainer();
            Flight flight = new Flight();
            flight.setStartDateTime(LocalDateTime.of(2020, 6, 3, 15, 22))
                    .setBasePrice(15000)
                    .setSeatsCount(70)
                    .setToWhere("New York")
                    .setFromWhere("Warsaw");
            User user = new User();
            user.setName("Jan")
                    .setSurname("Kowalski")
                    .setPasswordHash("asfaw5")
                    .setPhoneNumber(636363)
                    .setDateOfBirth(LocalDate.now())
                    .setLogin("koawl11");
            flightID = serviceContainer.getFlightService().save(flight);
            userID = serviceContainer.getUserService().save(user);
        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void destroyAll(){
        try {
            serviceContainer.getFlightService().getAll().stream().map(Flight::getID).forEach(id -> {
                try {
                    serviceContainer.getFlightService().delete(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            serviceContainer.getUserService().getAll().stream().map(User::getID).forEach(id -> {
                try {
                    serviceContainer.getUserService().delete(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            serviceContainer.getTicketService().getAll().stream().map(Ticket::getID).forEach(id ->{
                try {
                    serviceContainer.getTicketService().delete(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bookTicketTest() throws NoSuchFlightClassException, SQLException, InvalidBookingException, CancelingNotLongerPossibleException {
        Assertions.assertThrows(NoSuchFlightClassException.class, ()-> controllerContainer.getTicketController().bookTicket(userID, flightID, 2, "A"));
        controllerContainer.getTicketController().bookTicket(userID, flightID, 1, "Business");
        assert controllerContainer.getTicketController().getSeatsTaken(flightID).get(0) == 1;
        assert controllerContainer.getTicketController().getAvailableSeatsCount(flightID) == 69;
        Assertions.assertThrows(InvalidBookingException.class, ()-> controllerContainer.getTicketController().bookTicket(userID, flightID, 1, "First"));
        assert controllerContainer.getTicketController().isTicketAvailable(flightID);
        assert controllerContainer.getTicketController().getTicketsForFlight(flightID).size() == 1;
        assert controllerContainer.getTicketController().getTicketsForUserAndFlight(flightID, userID).size() == 1;
        controllerContainer.getTicketController().cancelTicket(serviceContainer.getTicketService().getAll().get(0).getID());
        assert controllerContainer.getTicketController().getTicketsForFlight(flightID).size() == 0;
    }

    @Test
    public void confirmationTest() throws NoSuchFlightClassException, SQLException, InvalidBookingException, IOException {
        controllerContainer.getTicketController().bookTicket(userID, flightID, 1, "Business");
        FileOutputStream fileOStream = new FileOutputStream("confTest.pdf");
        byte[] confirmationFile = controllerContainer.getTicketController().getTicketConfirmation(controllerContainer.getTicketController().getTicketsForFlight(flightID).get(0).getID());
        for(byte chunk: confirmationFile){
            fileOStream.write(chunk);
        }
        System.out.println("Saved confirmation file. Check \"confTest.pdf\".");
    }

}
