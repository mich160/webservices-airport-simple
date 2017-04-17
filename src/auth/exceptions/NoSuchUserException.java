package auth.exceptions;

public class NoSuchUserException extends Exception{
    public NoSuchUserException(String login){
        super("User "+login + " doesn't exist!");
    }
}
