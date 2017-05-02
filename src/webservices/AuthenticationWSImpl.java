package webservices;

import auth.exceptions.BadCredentialsException;
import auth.exceptions.LoginAlreadyTaken;
import controllers.ControllerContainer;
import utils.CalendarUtils;
import webservices.exceptions.DatabaseException;
import webservices.exceptions.InternalErrorException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebService(endpointInterface = "webservices.TicketWS")
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
    public String loginAndGetSessionToken() throws InternalErrorException, DatabaseException, BadCredentialsException {
        MessageContext messageContext = webServiceContext.getMessageContext();
        Map http_headers = (Map) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        List userList = (List) http_headers.get("Username");
        List passList = (List) http_headers.get("Password");

        String username = "";
        String password = "";

        if(userList!=null){
            username = userList.get(0).toString();
        }

        if(passList!=null){
            password = passList.get(0).toString();
        }
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
    public void registerUser(String login, String password, String name, String surname, XMLGregorianCalendar dateOfBirth, long phoneNumber) throws LoginAlreadyTaken, InternalErrorException, DatabaseException {
        try {
            controllerContainer.getAuthController().createUser(login, password, name, surname, CalendarUtils.xmlGregorianCalendarToLocalDate(dateOfBirth), phoneNumber);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new InternalErrorException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public boolean isLoginAvailable(String login) throws DatabaseException {
        try {
            return controllerContainer.getAuthController().isLoginAvailable(login);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    @Override
    public void logout(String sessionToken) throws DatabaseException {
        try {
            controllerContainer.getAuthController().destroySession(sessionToken);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }
}
