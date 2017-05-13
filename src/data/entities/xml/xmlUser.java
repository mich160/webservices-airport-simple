package data.entities.xml;

import data.entities.User;
import data.sqliteUtils.DateTimeUtils;

public class xmlUser {
    private long ID;
    private String name;
    private String surname;
    private String dateOfBirth;
    private long phoneNumber;
    private String login;

    public xmlUser(){}

    public xmlUser(User user){
        this.ID = user.getID();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.dateOfBirth = DateTimeUtils.JavaDateToStringDate(user.getDateOfBirth());
        this.phoneNumber = user.getPhoneNumber();
        this.login = user.getLogin();
    }

    public long getID() {
        return ID;
    }

    public xmlUser setID(long ID) {
        this.ID = ID;
        return this;
    }

    public String getName() {
        return name;
    }

    public xmlUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public xmlUser setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public xmlUser setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public xmlUser setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public xmlUser setLogin(String login) {
        this.login = login;
        return this;
    }
}
