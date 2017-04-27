package auth.exceptions;

public class LoginAlreadyTaken extends Exception {
    public LoginAlreadyTaken(String login) {
        super(login + " is already taken!");
    }
}
