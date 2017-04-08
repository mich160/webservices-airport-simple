package data.entities;

import java.time.LocalDate;

public class Client {
    private long ID;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private long phoneNumber;

    public long getID() {
        return ID;
    }

    public Client setID(long ID) {
        this.ID = ID;
        return this;
    }

    public String getName() {
        return name;
    }

    public Client setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Client setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Client setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public Client setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
