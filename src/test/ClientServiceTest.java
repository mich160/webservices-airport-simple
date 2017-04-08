package test;

import data.access.ClientService;
import data.entities.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ClientServiceTest {
    private static ClientService clientService;

    @BeforeAll
    public static void init(){
        try {
            clientService = new ClientService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CRUDTest(){ //przyjmimy ze w client jest 0 krotek
        Client client = new Client();
        client.setPhoneNumber(535353)
                .setDateOfBirth(LocalDate.of(1994,9,28))
                .setSurname("Kowalski")
                .setName("Jan");
        try {
            clientService.save(client);
            Client fetchedClient = clientService.getAll().get(0);
            assert fetchedClient.getName().equals(client.getName()) && fetchedClient.getSurname().equals(client.getSurname());
            assert fetchedClient.getPhoneNumber() == clientService.getByID(fetchedClient.getID()).getPhoneNumber();
            fetchedClient.setPhoneNumber(222);
            clientService.update(fetchedClient);
            assert clientService.getAll().get(0).getPhoneNumber() == 222;
            List<Client> allClients = clientService.getAll();
            clientService.delete(allClients.get(0).getID());
            assert clientService.getAll().isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
