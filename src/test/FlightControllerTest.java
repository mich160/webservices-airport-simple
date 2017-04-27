package test;

import controllers.FlightController;
import data.DataServiceContainer;
import data.access.FlightService;
import data.entities.Flight;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightControllerTest {
    private static FlightService flightService;
    private static FlightController flightController;
    private static List<Long> flightIDList;

    @BeforeAll
    public static void initFlights() throws SQLException {
        DataServiceContainer serviceContainer = DataServiceContainer.getDataServiceContainer();
        flightService = serviceContainer.getFlightService();
        flightController = new FlightController(serviceContainer);
        flightIDList = new ArrayList<>(5);

        Flight first = new Flight();
        first.setFromWhere("Poznan");
        first.setToWhere("Tokio");
        first.setSeatsCount(10);
        first.setStartDateTime(LocalDateTime.of(2016, 4, 15, 13, 24));

        Flight second = new Flight();
        second.setFromWhere("Warsaw")
                .setToWhere("New York")
                .setSeatsCount(100)
                .setStartDateTime(LocalDateTime.of(2016, 4, 15, 15, 10));

        Flight third = new Flight();
        third.setFromWhere("Poznan")
                .setToWhere("New York")
                .setSeatsCount(40)
                .setStartDateTime(LocalDateTime.of(2016,5,3,10,5));

        Flight fourth = new Flight();
        fourth.setFromWhere("Warsaw")
                .setToWhere("Moscow")
                .setSeatsCount(50)
                .setStartDateTime(LocalDateTime.now().plusDays(3).plusHours(2));

        Flight fifth = new Flight();
        fifth.setFromWhere("Poznan")
                .setToWhere("New York")
                .setSeatsCount(50)
                .setStartDateTime(LocalDateTime.of(2016, 5, 5, 8, 30));

        flightIDList.add(flightService.save(first));
        flightIDList.add(flightService.save(second));
        flightIDList.add(flightService.save(third));
        flightIDList.add(flightService.save(fourth));
        flightIDList.add(flightService.save(fifth));
    }

    @AfterAll
    public static void destroyFlights() throws SQLException {
        flightIDList.forEach(flightID -> {
            try {
                flightService.delete(flightID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void getFlightsByDayTest() throws SQLException {
        List<Flight> flights = flightController.getFlightsByDay(LocalDate.of(2016, 4, 15));
        assert flights.size() == 2;
    }

    @Test
    public void getFlightsInFiveDaysTest() throws SQLException {
        List<Flight> flights = flightController.getFlightsFromToXDaysAheadAfterToday("Warsaw", "Moscow", 5);
        assert flights.size() == 1;
    }

    @Test
    public void getFlightsAfterTimeTest() throws SQLException{
        List<Flight> flights = flightController.getFlightsAfterTime("Poznan", "New York", LocalDateTime.of(2016, 5, 4, 5, 8));
        assert flights.size() == 1;
    }
}
