package test;

import controllers.AuthController;
import auth.exceptions.BadCredentialsException;
import auth.exceptions.LoginAlreadyTaken;
import auth.exceptions.NoSuchUserException;
import data.DataServiceContainer;
import data.entities.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
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
    void createUserTest(){
        try {
            AuthController authController = new AuthController(DataServiceContainer.getDataServiceContainer());
            authController.createUser("test1", "pass", "Grazyna", "Nowak", LocalDate.now(), 53533532);
            User newUser = DataServiceContainer.getDataServiceContainer().getUserService().getByLogin("test1");
            System.out.println("Creating user test: ");
            System.out.println(newUser);
            assert newUser != null;
            authController.deleteUser("test1", "pass");
            newUser = DataServiceContainer.getDataServiceContainer().getUserService().getByLogin("test1");
            assert newUser == null;
        } catch (SQLException | NoSuchAlgorithmException | LoginAlreadyTaken | NoSuchUserException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createUserTestFail() throws NoSuchUserException {
        try {
            AuthController authController = new AuthController(DataServiceContainer.getDataServiceContainer());
            Assertions.assertThrows(NoSuchUserException.class, ()->authController.deleteUser("none", "none"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void renewSessionTest(){
        try {
            AuthController authController = new AuthController(DataServiceContainer.getDataServiceContainer());
            authController.setSessionLifespanInS(2);
            String token = authController.createSession(LOGIN, PASSWORD_HASH);
            assert authController.isSessionValid(token);
            authController.setSessionLifespanInS(3600);
            authController.renewSession(token);
            Thread.sleep(3000);
            assert authController.isSessionValid(token);
        } catch (SQLException | BadCredentialsException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
