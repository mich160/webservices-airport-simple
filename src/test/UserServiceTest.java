package test;

import data.ConnectionFactory;
import data.access.UserService;
import data.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UserServiceTest {
    private static UserService userService;

    @BeforeAll
    public static void init() {
        try {
            userService = new UserService(ConnectionFactory.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CRUDTest() { //przyjmimy ze w client jest 0 krotek
        User user = new User();
        user.setPhoneNumber(535353)
                .setDateOfBirth(LocalDate.of(1994, 9, 28))
                .setSurname("Kowalski")
                .setName("Jan")
                .setLogin("kowal88")
                .setPasswordHash("42g8gsad8");
        try {
            userService.save(user);
            User fetchedUser = userService.getAll().get(0);
            assert fetchedUser.getName().equals(user.getName()) && fetchedUser.getSurname().equals(user.getSurname());
            assert fetchedUser.getPhoneNumber() == userService.getByID(fetchedUser.getID()).getPhoneNumber();
            fetchedUser.setPhoneNumber(222);
            userService.update(fetchedUser);
            assert userService.getAll().get(0).getPhoneNumber() == 222;
            List<User> allUsers = userService.getAll();
            userService.delete(allUsers.get(0).getID());
            assert userService.getAll().isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
