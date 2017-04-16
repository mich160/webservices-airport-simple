package data.entities;

import java.time.LocalDateTime;

public class Session {
    private long ID;
    private String token;
    private long userID;
    private LocalDateTime expirationDateTime;

    public long getID() {
        return ID;
    }

    public Session setID(long ID) {
        this.ID = ID;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Session setToken(String token) {
        this.token = token;
        return this;
    }

    public long getUserID() {
        return userID;
    }

    public Session setUserID(long userID) {
        this.userID = userID;
        return this;
    }

    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public Session setExpirationDateTime(LocalDateTime expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
        return this;
    }
}
