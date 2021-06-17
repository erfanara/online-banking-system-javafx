package obsjApp.core;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String firstname;
    private String lastName;
    private String nationalCode;
    private String phoneNumber;
    private String email;
    // TODO: password (hash)?? https://duckduckgo.com/?t=ffab&q=how+to+store+password+in+java&ia=web
//    private String passHash;
//    private int incorrectPass;
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
