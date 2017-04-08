package data.entities;

import java.time.LocalDateTime;

public class Flight {
    private LocalDateTime startTime;
    private String fromWhere;
    private String toWhere;
    private int seatsCount;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Flight setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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
}
