package webservices.exceptions;

public class InvalidBookingException extends Exception {
    public InvalidBookingException(int seat) {
        super("Can't book ticket at seat " + seat + "!");
    }
}
