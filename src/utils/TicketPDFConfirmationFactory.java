package utils;

import data.entities.Flight;
import data.entities.Ticket;
import data.entities.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TicketPDFConfirmationFactory { //TODO
    public static PDDocument getTicketConfirmation(User user, Ticket ticket, Flight flight) throws IOException {
        PDDocument confirmationDocument = new PDDocument();
        PDPage page = new PDPage();
        PDFont fontNormal = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        confirmationDocument.addPage(page);
        try (PDPageContentStream contents = new PDPageContentStream(confirmationDocument, page)) {
            contents.beginText();
            contents.setLeading(30);
            contents.setFont(fontBold, 22);
            contents.newLineAtOffset(30, 700);
            contents.showText("POTWIERDZENIE REZERWACJI BILETU NR " + ticket.getID());
            contents.setFont(fontBold, 18);
            contents.newLine();
            contents.newLine();
            contents.showText("Dane biletu:");
            contents.setFont(fontNormal, 16);
            contents.newLine();
            contents.showText("Z: " + flight.getFromWhere());
            contents.newLine();
            contents.showText("Do: " + flight.getToWhere());
            contents.newLine();
            contents.showText("Odlot: " + flight.getStartDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
            contents.newLine();
            contents.showText("Cena: " + (ticket.getPrice() / 100) + " PLN");
            contents.newLine();
            contents.showText("Klasa: " + ticket.getFlightClass());
            contents.newLine();
            contents.showText("Miejsce: " + ticket.getSeat());
            contents.newLine();
            contents.showText("-------------");
            contents.setFont(fontBold, 18);
            contents.newLine();
            contents.showText("Dane pasazera:");
            contents.setFont(fontNormal, 16);
            contents.newLine();
            contents.showText("Imie: " + user.getName());
            contents.newLine();
            contents.showText("Nazwisko: " + user.getSurname());
            contents.setFont(fontNormal, 15);
            contents.newLineAtOffset(0, -300);
            contents.showText(flight.getStartDateTime().getYear() + " firma X");
            contents.endText();
            contents.close();
        }
        return confirmationDocument;
    }
}
