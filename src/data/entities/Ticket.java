package data.entities;

public class Ticket {
    private long ID;
    private long flightID;
    private long clientID;
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

    public long getClientID() {
        return clientID;
    }

    public Ticket setClientID(long clientID) {
        this.clientID = clientID;
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
}
