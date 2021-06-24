package obsjApp.core;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: it seems this class is only used by server package ,so we can move it to the server package
public class User implements Serializable {
    private String firstname;
    private String lastName;
    private String nationalCode;
    private String phoneNumber;
    private String email;
    // encrypted password along with it's salt
    private String passHash;
    private String passSalt;

    // TODO: counting incorrect passwords for protection
    //    private int incorrectPass;


    private final ArrayList<Account> accounts;
    // TODO : favorite accounts (we need to store favorite accounts maybe ?)

    public User(String firstname,
                String lastName,
                String nationalCode,
                String phoneNumber,
                String email,
                String password) throws Exception {
        this.firstname = firstname;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
        this.email = email;

        this.passSalt = SecurePass.getNewSalt();
        this.passHash = SecurePass.getPassHash(password, passSalt);

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

//    public void closeAcc()
}
