package webservices;

import auth.exceptions.BadCredentialsException;
import auth.exceptions.LoginAlreadyTaken;
import controllers.ControllerContainer;
import data.entities.xml.xmlUser;
import data.sqliteUtils.DateTimeUtils;
import webservices.exceptions.DatabaseException;
import webservices.exceptions.InternalErrorException;
import webservices.exceptions.NotLoggedException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebService(endpointInterface = "webservices.AuthenticationWS")
@HandlerChain(file = "handler-chain.xml")
public class AuthenticationWSImpl implements AuthenticationWS{
    @Resource
    private WebServiceContext webServiceContext;

    private ControllerContainer controllerContainer;

    @PostConstruct
    public void init() {
        try {
            controllerContainer = ControllerContainer.getControllerContainer();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String loginAndGetSessionToken(@WebParam(name = "username") String username,
                                          @WebParam(name = "password") String password) throws InternalErrorException, DatabaseException, BadCredentialsException {

        try {
            return controllerContainer.getAuthController().createSession(username, controllerContainer.getAuthController().getPasswordHash(password));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new InternalErrorException(e);
        }
    }

    @Override
    public void registerUser(
            @WebParam(name = "login") String login,
            @WebParam(name = "password") String password,
            @WebParam(name = "name") String name,
            @WebParam(name = "surname") String surname,
            @WebParam(name = "dateOfBirth") String dateOfBirth,
            @WebParam(name = "phoneNumber") long phoneNumber) throws LoginAlreadyTaken, InternalErrorException, DatabaseException {
        try {
            controllerContainer.getAuthController().createUser(login, password, name, surname, DateTimeUtils.stringDateToJavaDate(dateOfBirth), phoneNumber);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new InternalErrorException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public xmlUser getCurrentUser(@WebParam(name = "sessionToken") String sessionToken) throws DatabaseException, NotLoggedException {
        try {
            return new xmlUser(controllerContainer.getAuthController().getUserBySession(sessionToken));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public boolean isLoginAvailable(@WebParam(name = "login") String login) throws DatabaseException {
        try {
            return controllerContainer.getAuthController().isLoginAvailable(login);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public void logout(@WebParam(name = "sessionToken") String sessionToken) throws DatabaseException {
        try {
            controllerContainer.getAuthController().destroySession(sessionToken);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }
}
