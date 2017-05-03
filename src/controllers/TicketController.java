package controllers;

import webservices.exceptions.CancelingNotLongerPossibleException;
import webservices.exceptions.InvalidBookingException;
import webservices.exceptions.NoSuchFlightClassException;
import data.DataServiceContainer;
import data.entities.Flight;
import data.entities.Ticket;
import data.entities.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import utils.TicketPDFConfirmationFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TicketController { //TODO unit tests
    private DataServiceContainer dataServiceContainer;

    public TicketController(DataServiceContainer dataServiceContainer) {
        this.dataServiceContainer = dataServiceContainer;
    }

    public boolean isTicketAvailable(long flightID) throws SQLException {
        return getAvailableSeatsCount(flightID) > 0 && dataServiceContainer.getFlightService().getByID(flightID).getStartDateTime().isAfter(LocalDateTime.now());
    }

    public List<Integer> getSeatsTaken(long flightID) throws SQLException {
        List<Ticket> tickets = dataServiceContainer.getTicketService().getByFlightID(flightID);
        return tickets.stream().map((Ticket::getSeat)).collect(Collectors.toList());
    }

    public int getAvailableSeatsCount(long flightID) throws SQLException {
        Flight flight = dataServiceContainer.getFlightService().getByID(flightID);
        return flight.getSeatsCount() - getSeatsTaken(flightID).size();
    }

    public void bookTicket(long userID, long flightID, int seat, String flightClass) throws SQLException, NoSuchFlightClassException, InvalidBookingException {
        if (!dataServiceContainer.getClassesService().flightClassExists(flightClass)) {
            throw new NoSuchFlightClassException(flightClass);
        }
        if (isTicketAvailable(flightID) && !getSeatsTaken(flightID).contains(seat)) {
            Ticket newTicket = new Ticket();
            newTicket.setSeat(seat)
                    .setFlightClass(flightClass)
                    .setFlightID(flightID)
                    .setUserID(userID)
                    .setPrice((long) (dataServiceContainer.getClassesService().getPriceRate(flightClass) * dataServiceContainer.getFlightService().getByID(flightID).getBasePrice()));
            dataServiceContainer.getTicketService().save(newTicket);
        } else {
            throw new InvalidBookingException(seat);
        }
    }

    public void cancelTicket(long ticketID) throws SQLException, CancelingNotLongerPossibleException {
        Ticket ticket = dataServiceContainer.getTicketService().getByID(ticketID);
        Flight flight = dataServiceContainer.getFlightService().getByID(ticket.getFlightID());
        if (flight.getStartDateTime().isAfter(LocalDateTime.now())) {
            dataServiceContainer.getTicketService().delete(ticketID);
        } else {
            throw new CancelingNotLongerPossibleException(ticket);
        }
    }

    public List<Ticket> getTicketsForFlight(long flightID) throws SQLException {
        return dataServiceContainer.getTicketService().getByFlightID(flightID);
    }

    public List<Ticket> getTicketsForUser(long userID) throws SQLException{
        return dataServiceContainer.getTicketService().getByUserID(userID);
    }

    public List<Ticket> getTicketsForUserAndFlight(long flightID, long userID) throws SQLException {
        return dataServiceContainer.getTicketService().getByUserAndFlightID(userID, flightID);
    }

    public byte[] getTicketConfirmation(long ticketID) throws SQLException, IOException {
        Ticket ticket = dataServiceContainer.getTicketService().getByID(ticketID);
        User user = dataServiceContainer.getUserService().getByID(ticket.getUserID());
        Flight flight = dataServiceContainer.getFlightService().getByID(ticket.getUserID());

        PDDocument result = TicketPDFConfirmationFactory.getTicketConfirmation(user, ticket, flight);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        result.save(outputStream);
        result.close();
        return outputStream.toByteArray();
    }

}
