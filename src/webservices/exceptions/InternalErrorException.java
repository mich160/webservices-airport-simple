package webservices.exceptions;

public class InternalErrorException extends Exception{
    public InternalErrorException(Throwable cause){
        super("Internal server error.", cause);
    }
}
