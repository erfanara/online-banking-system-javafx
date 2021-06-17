package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name;
    private String lastName;
    private String nationalCode;
    private String phoneNumber;
    private String email;
    private ArrayList<Account> accounts;

    public User(){}

    public User(String name, String lastName, String nationalCode, String phoneNumber, String email) {
        this.name = name;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
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
