package auth.exceptions;

/**
 * Created by Mieho on 23.04.2017.
 */
public class NoSuchFlightClassException extends Exception {
    public NoSuchFlightClassException(String flightClass) {
        super("There is no " + flightClass + " flight class!");
    }
}
