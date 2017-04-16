package data.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FlightClassesService {
    private Set<String> flightClasses;

    public FlightClassesService(){
        flightClasses = new TreeSet<>();

        flightClasses.add("Business");
        flightClasses.add("Premium");
        flightClasses.add("First");
        flightClasses.add("Economy");
    }

    public boolean flightClassExists(String flightClass){
        return flightClasses.contains(flightClass);
    }

    public List<String> getAllFlightClasses(){
        return new ArrayList<>(flightClasses);
    }
}
