package data.constants;

import java.util.*;

public class FlightClassesService {
    private Set<String> flightClasses;
    private HashMap<String, Double> pricesRates;

    public FlightClassesService(){
        flightClasses = new TreeSet<>();

        flightClasses.add("Business");
        flightClasses.add("Premium");
        flightClasses.add("First");
        flightClasses.add("Economy");

        pricesRates = new HashMap<>();

        pricesRates.put("Business", 3.0);
        pricesRates.put("Premium", 2.5);
        pricesRates.put("First", 1.5);
        pricesRates.put("Economy", 1.0);
    }

    public boolean flightClassExists(String flightClass){
        return flightClasses.contains(flightClass);
    }

    public List<String> getAllFlightClasses(){
        return new ArrayList<>(flightClasses);
    }

    public double getPriceRate(String flightClass){
        return pricesRates.get(flightClass);
    }
}
