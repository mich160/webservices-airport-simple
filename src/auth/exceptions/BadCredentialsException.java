package auth.exceptions;

public class BadCredentialsException extends Exception{
    public BadCredentialsException(){
        super("Invalid login or password!");
    }
}
