package webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.datatype.XMLGregorianCalendar;

@WebService
@SOAPBinding
public interface AuthenticationWS {//TODO IMPLEMENT

    @WebMethod
    String loginAndGetSessionToken(); //potrzeba credentials w naglowku

    @WebMethod
    void registerUser(String login, String password, String name, String surname, XMLGregorianCalendar dateOfBirth, long phoneNumber);

    @WebMethod
    boolean isLoginAvailable(String login);

    @WebMethod
    void logout(String sessionToken);
}
