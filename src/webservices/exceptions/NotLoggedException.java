package webservices.exceptions;

public class NotLoggedException extends Exception {
    public NotLoggedException(){
        super("This session has expired!");
    }
}
