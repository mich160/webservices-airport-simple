package webservices.exceptions;

public class SessionExpiredException extends Exception {
    public SessionExpiredException(){
        super("This session has expired!");
    }
}
