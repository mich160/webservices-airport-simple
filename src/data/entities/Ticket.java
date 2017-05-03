package data.entities;

import java.io.Serializable;

public class Ticket implements Serializable{
    private long ID;
    private long flightID;
    private long userID;
    private long price;
    private String flightClass;
    private int seat;

    public long getID() {
        return ID;
    }

    public Ticket setID(long ID) {
        this.ID = ID;
        return this;
    }

    public long getFlightID() {
        return flightID;
    }

    public Ticket setFlightID(long flightID) {
        this.flightID = flightID;
        return this;
    }

    public long getUserID() {
        return userID;
    }

    public Ticket setUserID(long userID) {
        this.userID = userID;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public Ticket setPrice(long price) {
        this.price = price;
        return this;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public Ticket setFlightClass(String flightClass) {
        this.flightClass = flightClass;
        return this;
    }

    public int getSeat() {
        return seat;
    }

    public Ticket setSeat(int seat) {
        this.seat = seat;
        return this;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ID=" + ID +
                ", flightID=" + flightID +
                ", userID=" + userID +
                ", price=" + price +
                ", flightClass='" + flightClass + '\'' +
                ", seat=" + seat +
                '}';
    }
}
