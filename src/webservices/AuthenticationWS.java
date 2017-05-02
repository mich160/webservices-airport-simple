package webservices;

import auth.exceptions.BadCredentialsException;
import auth.exceptions.LoginAlreadyTaken;
import webservices.exceptions.DatabaseException;
import webservices.exceptions.InternalErrorException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.datatype.XMLGregorianCalendar;

@WebService
@SOAPBinding
public interface AuthenticationWS {//TODO IMPLEMENT

    @WebMethod
    String loginAndGetSessionToken() throws InternalErrorException, DatabaseException, BadCredentialsException; //potrzeba credentials w naglowku

    @WebMethod
    void registerUser(String login, String password, String name, String surname, XMLGregorianCalendar dateOfBirth, long phoneNumber) throws LoginAlreadyTaken, InternalErrorException, DatabaseException;

    @WebMethod
    boolean isLoginAvailable(String login) throws DatabaseException;

    @WebMethod
    void logout(String sessionToken) throws DatabaseException;
}
