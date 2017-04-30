package init;

import data.DataServiceContainer;
import data.access.FlightService;
import data.entities.Flight;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class InitDB {
    public static final int FLIGHT_COUNT = 1000;
    public static void main(String[] args) {
        try {
            DataServiceContainer serviceContainer = DataServiceContainer.getDataServiceContainer();
            FlightService flightService = serviceContainer.getFlightService();
            List<String> allFromCities = serviceContainer.getCitiesService().allFromCities();
            List<String> allToCities = serviceContainer.getCitiesService().allToCities();
            Random randomGen = new Random();

            Flight newFlight = new Flight();
            for(int index = 0; index < FLIGHT_COUNT; index++){
                newFlight.setFromWhere(allFromCities.get(randomGen.nextInt(allFromCities.size())));
                newFlight.setToWhere(allToCities.get(randomGen.nextInt(allToCities.size())));
                newFlight.setSeatsCount(50 + randomGen.nextInt(61));
                newFlight.setBasePrice(30000 + randomGen.nextInt(10000));
                newFlight.setStartDateTime(LocalDateTime.now().plusHours(randomGen.nextInt(300)));
                flightService.save(newFlight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
