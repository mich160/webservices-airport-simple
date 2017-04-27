package data.entities;

import java.time.LocalDateTime;

public class Flight {
    private long ID;
    private LocalDateTime startDateTime;
    private String fromWhere;
    private String toWhere;
    private int seatsCount;
    private long basePrice;

    public long getID() {
        return ID;
    }

    public Flight setID(long ID) {
        this.ID = ID;
        return this;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public Flight setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public Flight setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
        return this;
    }

    public String getToWhere() {
        return toWhere;
    }

    public Flight setToWhere(String toWhere) {
        this.toWhere = toWhere;
        return this;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public Flight setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
        return this;
    }

    public long getBasePrice() {
        return basePrice;
    }

    public Flight setBasePrice(long basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "ID=" + ID +
                ", startDateTime=" + startDateTime +
                ", fromWhere='" + fromWhere + '\'' +
                ", toWhere='" + toWhere + '\'' +
                ", seatsCount=" + seatsCount +
                ", basePrice=" + basePrice +
                '}';
    }
}
