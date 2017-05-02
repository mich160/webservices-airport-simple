package webservices.exceptions;

public class NoSuchFlightClassException extends Exception {
    public NoSuchFlightClassException(String flightClass) {
        super("There is no " + flightClass + " flight class!");
    }
}
