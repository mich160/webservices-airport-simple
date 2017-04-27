package controllers;

import data.DataServiceContainer;
import data.entities.Flight;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FlightController {
    private DataServiceContainer dataServiceContainer;

    public FlightController(DataServiceContainer dataServiceContainer) {
        this.dataServiceContainer = dataServiceContainer;
    }

    public boolean isFromCityValid(String string) {
        return dataServiceContainer.getCitiesService().cityFromExists(string);
    }

    public boolean isToCityValid(String string) {
        return dataServiceContainer.getCitiesService().cityToExists(string);
    }

    public List<String> getAllFromCities() {
        return dataServiceContainer.getCitiesService().allFromCities();
    }

    public List<String> getAllToCities() {
        return dataServiceContainer.getCitiesService().allToCities();
    }

    public List<Flight> getFlightsByDay(LocalDate date) throws SQLException {
        return dataServiceContainer.getFlightService().getByDate(date);
    }

    public List<Flight> getFlightsFromToXDaysAheadAfterToday(String from, String to, int days) throws SQLException {
        return dataServiceContainer.getFlightService().getXDaysAfterNow(from, to, days);
    }

    public List<Flight> getFlightsAfterTime(String from, String to, LocalDateTime localDateTime) throws SQLException {
        return dataServiceContainer.getFlightService().getAfterTime(from, to, localDateTime);
    }

}
