package auth.exceptions;

/**
 * Created by Mieho on 23.04.2017.
 */
public class InvalidBookingException extends Exception {
    public InvalidBookingException(int seat) {
        super("Can't book ticket at seat " + seat + "!");
    }
}
