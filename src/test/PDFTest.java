package test;

import data.entities.Flight;
import data.entities.Ticket;
import data.entities.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;
import utils.TicketPDFConfirmationFactory;

import java.io.IOException;
import java.time.LocalDateTime;

public class PDFTest {
    @Test
    public void savePDF() throws IOException {
        User user = new User();
        user.setName("Jan")
                .setSurname("Kowalski");
        Flight flight = new Flight();
        flight.setToWhere("New York")
                .setFromWhere("Warsaw")
                .setStartDateTime(LocalDateTime.now());
        Ticket ticket = new Ticket();
        ticket.setPrice(30000)
                .setSeat(1)
                .setFlightClass("Business");

        PDDocument document = TicketPDFConfirmationFactory.getTicketConfirmation(user, ticket, flight);
        document.save("test.pdf");
    }
}
