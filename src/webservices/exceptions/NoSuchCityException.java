package webservices.exceptions;

public class NoSuchCityException extends Exception {
    public NoSuchCityException(String name) {
        super("City " + name + " doesn't exists!");
    }
}
