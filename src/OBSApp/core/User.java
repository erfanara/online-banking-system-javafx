package OBSApp.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import OBSApp.core.SavingAccount;
import OBSApp.core.SecurePass;
import OBSApp.core.Bill;
import OBSApp.core.Account;

// TODO: it seems this class is only used by server package ,so we can move it to the server package
public class User implements Serializable {
    // THESE STATIC FIELDS WILL NOT SERIALIZED , so we will initialize them at the start of our server manually
    public static int numberOfUsers;

    // this map is global for all of users that key represent Acc id using String
    public static Map<String, Account> allAccounts = null;


    private String firstname, lastName, nationalCode, phoneNumber, email;

    private final LocalDateTime signUpDate = LocalDateTime.now();

    // encrypted password along with it's salt
    private String passHash;
    private String passSalt;

    // TODO: counting incorrect passwords for protection
    //    private int incorrectPass;

    // these maps are just for one user and contains user owned Accounts
    // that represents <String id, Account> or <String alias,Account>
    private final Map<String, Account> userAccountsById = new LinkedHashMap<String, Account>();
    private final Map<String, Account> userAccountsByAlias = new LinkedHashMap<String, Account>();

    private final ArrayList<Bill> issuedBills = new ArrayList<Bill>();
    private BigDecimal remainingBill;
    // TODO : favorite accounts (we need to store favorite accounts maybe ?)

    public User(String firstname,
                String lastName,
                String nationalCode,
                String phoneNumber,
                String email,
                String password) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        setPassHash(password);
        numberOfUsers++;
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
        return (Account[]) (userAccountsById.values().toArray(new Account[0]));
    }

    public Account getAccById(String id) {
        return userAccountsById.get(id);
    }

    public Account getAccByAlias(String alias) {
        return userAccountsByAlias.get(alias);
    }

    public String[] getAllAccIds() {
        return userAccountsById.keySet().toArray(new String[0]);
    }

    public static int getNumberOfUsers() {
        return numberOfUsers;
    }

    // returns id of created Acc
    public String createAcc(Account.Type type, String alias, String accPassword){
        Account acc = null;
        switch (type) {
            case SAVING -> {
                acc = new SavingAccount(accPassword, alias, this);
            }
            case CHECKING -> {
                acc = new Account(accPassword, alias, this);
            }
        }
        allAccounts.put(acc.getId(), acc);
        userAccountsById.put(acc.getId(), acc);
        if (alias != null)
            userAccountsByAlias.put(alias, acc);

        return acc.getId();
    }
//    public void closeAcc()

    // authenticate for User
    public boolean auth(String password) {
        return this.passHash.equals(SecurePass.getPassHash(password, this.passSalt));
    }

    public void setPassHash(String newPassword) {
        this.passSalt = SecurePass.getNewSalt();
        this.passHash = SecurePass.getPassHash(newPassword, passSalt);
    }

    public static Account getAccByIdInAll(String id) {
        return allAccounts.get(id);
    }

    public static Map<String, Account> getAllAccountsMap() {
        return allAccounts;
    }

    public void removeAcc(String accId) {
        // this method assumes that accId is valid for this user
        allAccounts.remove(accId);
        userAccountsById.remove(accId);
        userAccountsByAlias.remove(accId);
    }
}
