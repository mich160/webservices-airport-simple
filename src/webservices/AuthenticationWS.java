package webservices;

import auth.exceptions.BadCredentialsException;
import auth.exceptions.LoginAlreadyTaken;
import data.entities.xml.xmlUser;
import webservices.exceptions.DatabaseException;
import webservices.exceptions.InternalErrorException;
import webservices.exceptions.NotLoggedException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

@WebService
@SOAPBinding
public interface AuthenticationWS {

    @WebMethod
    String loginAndGetSessionToken(@WebParam(name = "username") String username,
                                   @WebParam(name = "password") String password) throws InternalErrorException, DatabaseException, BadCredentialsException;

    @WebMethod
    void registerUser(@WebParam(name = "login") String login,
                      @WebParam(name = "password") String password,
                      @WebParam(name = "name") String name,
                      @WebParam(name = "surname") String surname,
                      @WebParam(name = "dateOfBirth") String dateOfBirth,
                      @WebParam(name = "phoneNumber") long phoneNumber) throws LoginAlreadyTaken, InternalErrorException, DatabaseException;

    @WebMethod
    xmlUser getCurrentUser(@WebParam(name = "sessionToken") String sessionToken) throws DatabaseException, NotLoggedException;

    @WebMethod
    boolean isLoginAvailable(@WebParam(name = "login") String login) throws DatabaseException;

    @WebMethod
    void logout(@WebParam(name = "sessionToken") String sessionToken) throws DatabaseException;
}
