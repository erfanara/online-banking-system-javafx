package obsjApp.core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

// TODO: it seems this class is only used by server package ,so we can move it to the server package
public class User implements Serializable {
    private String firstname, lastName, nationalCode, phoneNumber, email;

    private final LocalDateTime signUpDate = LocalDateTime.now();

    // encrypted password along with it's salt
    private String passHash;
    private String passSalt;

    // TODO: counting incorrect passwords for protection
    //    private int incorrectPass;

    // this map is global for all of users that key represent Acc id using String
    private static final Map<String, Account> allAccounts = new LinkedHashMap<String, Account>();

    // these maps are just for one user and contains user owned Accounts
    // that represents <String id, Account> or <String alias,Account>
    private final Map<String, Account> userAccountsById = new LinkedHashMap<String, Account>();
    private final Map<String, Account> userAccountsByAlias = new LinkedHashMap<String, Account>();

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

        setPassHash(password);
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

    public Account[] getAccounts() {
        return (Account[]) (userAccountsById.values().toArray());
    }

    // TODO: Account type ...
    public void createAcc(Account.Type type, String accPassword) throws Exception {
        Account acc = new Account(accPassword, null);
        allAccounts.put(acc.getId(), acc);
        userAccountsById.put(acc.getId(), acc);
    }

    public void createAcc(Account.Type type, String alias, String accPassword) throws Exception {
        Account acc = new Account(accPassword, alias);
        allAccounts.put(acc.getId(), acc);
        userAccountsById.put(acc.getId(), acc);
        userAccountsByAlias.put(alias, acc);
    }
//    public void closeAcc()

    // authenticate for User
    public boolean auth(String password) throws Exception {
        return this.passHash.equals(SecurePass.getPassHash(password, this.passSalt));
    }

    public void setPassHash(String newPassword) throws Exception {
        this.passSalt = SecurePass.getNewSalt();
        this.passHash = SecurePass.getPassHash(newPassword, passSalt);
    }
}
