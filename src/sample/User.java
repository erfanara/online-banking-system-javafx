package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private final String firstname;
    private final String lastName;
    private final String nationalCode;
    private final String phoneNumber;
    private final String email;
    private final ArrayList<Account> accounts;

    public User(String firstname, String lastName, String nationalCode, String phoneNumber, String email) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        accounts = new ArrayList<>();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
