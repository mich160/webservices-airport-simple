package data.entities.xml;

import data.entities.Flight;
import data.sqliteUtils.DateTimeUtils;

public class xmlFlight {
    private long ID;
    private String startDateTime;
    private String fromWhere;
    private String toWhere;
    private int seatsCount;
    private long basePrice;

    public xmlFlight(){}

    public xmlFlight(Flight flight){
        this.ID = flight.getID();
        this.startDateTime = DateTimeUtils.JavaDateTimeToStringDateTime(flight.getStartDateTime());
        this.fromWhere = flight.getFromWhere();
        this.toWhere = flight.getToWhere();
        this.seatsCount = flight.getSeatsCount();
        this.basePrice = flight.getBasePrice();
    }

    public long getID() {
        return ID;
    }

    public xmlFlight setID(long ID) {
        this.ID = ID;
        return this;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public xmlFlight setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public xmlFlight setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
        return this;
    }

    public String getToWhere() {
        return toWhere;
    }

    public xmlFlight setToWhere(String toWhere) {
        this.toWhere = toWhere;
        return this;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public xmlFlight setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
        return this;
    }

    public long getBasePrice() {
        return basePrice;
    }

    public xmlFlight setBasePrice(long basePrice) {
        this.basePrice = basePrice;
        return this;
    }
}
