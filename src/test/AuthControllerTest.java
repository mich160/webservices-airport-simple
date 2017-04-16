package test;

import auth.AuthController;
import auth.exceptions.BadCredentialsException;
import data.DataServiceContainer;
import data.entities.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

public class AuthControllerTest {
    public static final String LOGIN = "kowal44", PASSWORD_HASH = "aqwwww1";

    @BeforeAll
    static void init(){
        User user = new User();
        user.setName("Jan")
                .setSurname("Kowalski")
                .setDateOfBirth(LocalDate.now())
                .setLogin(LOGIN)
                .setPhoneNumber(535533)
                .setPasswordHash(PASSWORD_HASH);
        try {
            DataServiceContainer.getDataServiceContainer().getUserService().save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @AfterAll
    static void deInit(){
        try {
            User user = DataServiceContainer.getDataServiceContainer().getUserService().getByLogin(LOGIN);
            DataServiceContainer.getDataServiceContainer().getUserService().delete(user.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void createSessionTest(){
        try {
            AuthController authController = new AuthController(DataServiceContainer.getDataServiceContainer());
            String token = authController.createSession(LOGIN, PASSWORD_HASH);
            assert DataServiceContainer.getDataServiceContainer().getSessionService().getByToken(token) != null;
            assert authController.isSessionValid(token);
            authController.destroySession(token);
            assert DataServiceContainer.getDataServiceContainer().getSessionService().getByToken(token) == null;
            assert !authController.isSessionValid(token);

        } catch (SQLException | BadCredentialsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void expireSessionTest(){
        try {
            AuthController authController = new AuthController(DataServiceContainer.getDataServiceContainer());
            authController.setSessionLifespanInS(2);
            String token = authController.createSession(LOGIN, PASSWORD_HASH);
            assert authController.isSessionValid(token);
            Thread.sleep(3000);
            assert !authController.isSessionValid(token);
        } catch (SQLException | BadCredentialsException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void renewSessionTest(){
        //TODO
    }
}
