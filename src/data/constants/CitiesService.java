package data.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CitiesService {
    private Set<String> toCities;
    private Set<String> fromCities;

    public CitiesService(){
        toCities = new TreeSet<>();
        fromCities = new TreeSet<>();
        //TO CITIES
        toCities.add("New York");
        toCities.add("Moscow");
        toCities.add("Sydney");
        toCities.add("Tokio");
        toCities.add("Berlin");
        toCities.add("Paris");
        toCities.add("Madrid");
        //FROM CITIES
        fromCities.add("Warsaw");
        fromCities.add("Poznan");
    }

    public boolean cityFromExists(String city) {
        return fromCities.contains(city);
    }

    public boolean cityToExists(String city) {
        return toCities.contains(city);
    }

    public List<String> allFromCities() {
        return new ArrayList<>(fromCities);
    }

    public List<String> allToCities() {
        return new ArrayList<>(toCities);
    }

}
