package controllers;

import auth.AllowedMachinesRegistry;
import auth.exceptions.BadCredentialsException;
import auth.exceptions.LoginAlreadyTaken;
import auth.exceptions.NoSuchUserException;
import data.DataServiceContainer;
import data.access.UserService;
import data.entities.Session;
import data.entities.User;
import webservices.exceptions.NotLoggedException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuthController {
    private final static long SESSION_LIFESPAN_IN_S = 3600;

    private DataServiceContainer dataServiceContainer;
    private AllowedMachinesRegistry allowedMachinesRegistry;
    private long sessionLifespanInS;
    private MessageDigest passwordDigest;

    public AuthController(DataServiceContainer dataServiceContainer) throws SQLException {
        this.dataServiceContainer = dataServiceContainer;
        this.allowedMachinesRegistry = new AllowedMachinesRegistry();
        this.sessionLifespanInS = SESSION_LIFESPAN_IN_S;
    }

    public String createSession(String login, String passwordHash) throws BadCredentialsException, SQLException {
        deleteExpiredSessions();
        UserService userService = dataServiceContainer.getUserService();
        User user = userService.getByLogin(login);
        if (user != null && user.getPasswordHash().equals(passwordHash)) {
            Session session = dataServiceContainer.getSessionService().getByUserID(user.getID());
            if (session != null) {
                renewSession(session.getToken());
                return session.getToken();
            } else {
                session = new Session();
                String sessionToken = UUID.randomUUID().toString();
                while (dataServiceContainer.getSessionService().getByToken(sessionToken) != null) {
                    sessionToken = UUID.randomUUID().toString();
                }
                session.setUserID(user.getID())
                        .setToken(sessionToken)
                        .setExpirationDateTime(LocalDateTime.now().plusSeconds(sessionLifespanInS));
                dataServiceContainer.getSessionService().save(session);
                return sessionToken;
            }
        } else {
            throw new BadCredentialsException();
        }
    }

    public void destroySession(String sessionToken) throws SQLException {
        deleteExpiredSessions();
        Session session = dataServiceContainer.getSessionService().getByToken(sessionToken);
        if (session != null) {
            dataServiceContainer.getSessionService().delete(session.getID());
        }
    }

    public void renewSession(String sessionToken) throws SQLException {
        deleteExpiredSessions();
        Session session = dataServiceContainer.getSessionService().getByToken(sessionToken);
        if (session != null) {
            session.setExpirationDateTime(LocalDateTime.now().plusSeconds(sessionLifespanInS));
            dataServiceContainer.getSessionService().update(session);
        }
    }

    public boolean isSessionValid(String sessionToken) throws SQLException {
        deleteExpiredSessions();
        Session session = dataServiceContainer.getSessionService().getByToken(sessionToken);
        return session != null && session.getExpirationDateTime().isAfter(LocalDateTime.now());
    }

    public void deleteExpiredSessions() throws SQLException {
        dataServiceContainer.getSessionService().deleteExpiredSessions();
    }

    public void createUser(String login, String password, String name, String surname, LocalDate dateOfBirth, long phoneNumber) throws NoSuchAlgorithmException, SQLException, LoginAlreadyTaken {
        if (dataServiceContainer.getUserService().getByLogin(login) == null) {
            User user = new User();
            user.setName(name)
                    .setSurname(surname)
                    .setLogin(login)
                    .setDateOfBirth(dateOfBirth)
                    .setPhoneNumber(phoneNumber)
                    .setPasswordHash(getPasswordHash(password));
            dataServiceContainer.getUserService().save(user);
        } else {
            throw new LoginAlreadyTaken(login);
        }
    }

    public User getUserBySession(String sessionToken) throws SQLException, NotLoggedException {
        Session session = dataServiceContainer.getSessionService().getByToken(sessionToken);
        if(session != null){
            return dataServiceContainer.getUserService().getByID(session.getUserID());
        }
        else {
            throw new NotLoggedException();
        }
    }

    public void deleteUser(String login, String password) throws SQLException, NoSuchAlgorithmException, NoSuchUserException {
        User user = dataServiceContainer.getUserService().getByLogin(login);
        if (this.passwordDigest == null) {
            this.passwordDigest = MessageDigest.getInstance("SHA-256");
        }
        this.passwordDigest.update(password.getBytes());
        String passwordHash = new String(this.passwordDigest.digest());
        if (user != null && passwordHash.equals(user.getPasswordHash())) {
            dataServiceContainer.getUserService().delete(user.getID());
        } else {
            throw new NoSuchUserException(login);
        }
    }

    public String getPasswordHash(String password) throws NoSuchAlgorithmException {
        if (this.passwordDigest == null) {
            this.passwordDigest = MessageDigest.getInstance("SHA-256");
        }
        this.passwordDigest.update(password.getBytes());
        String result = new String(this.passwordDigest.digest());
        this.passwordDigest.reset();
        return result;
    }

    public boolean isIPAllowed(String ip) {
        return allowedMachinesRegistry.isIPAllowed(ip);
    }

    public boolean isLoginAvailable(String login) throws SQLException {
        return dataServiceContainer.getUserService().getByLogin(login) == null;
    }

    public long getSessionLifespanInS() {
        return sessionLifespanInS;
    }

    public AuthController setSessionLifespanInS(long sessionLifespanInS) {
        this.sessionLifespanInS = sessionLifespanInS;
        return this;
    }
}
