package obsjApp.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Account implements Serializable {
    public static final long ACC_ID_START = 6037000000000000L;

    // TODO: type of Account should specified using enum or maybe inheritance ?
    public static enum Type {

    }

    private String alias;

    // Account id is a 16-digit number that we obtain this number using the hashcode of the Account object
    private String id;

    private BigDecimal Balance;

    private final LocalDateTime creationDate = LocalDateTime.now();

    // encrypted password along with it's salt
    private String passHash;
    private String passSalt;

    private final ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    // alias in parameters can be null
    public Account(String password, String alias) throws Exception {
        this.alias = alias;
        this.id = String.valueOf(this.hashCode() + ACC_ID_START);
        setPassHash(password);
    }

    public Account(String password, String alias, BigDecimal balance) throws Exception {
        this(password, alias);
        this.Balance = balance;
    }

    public void withdraw(BigDecimal amount) {
        if (getBalance().compareTo(amount) < 0) {
            transactions.add(new Transaction(Transaction.Type.WITHDRAW, amount, getBalance(), false));
            return;
        }
        setBalance(getBalance().add(amount.negate()));
        transactions.add(new Transaction(Transaction.Type.WITHDRAW, amount, getBalance(), true));
    }

    public void Deposit(BigDecimal amount) {
        setBalance(getBalance().add(amount));
        transactions.add(new Transaction(Transaction.Type.DEPOSIT, amount, getBalance(), true));
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    // authenticate for Account
    public boolean auth(String password) throws Exception {
        return this.passHash.equals(SecurePass.getPassHash(password, this.passSalt));
    }

    public void setPassHash(String newPassword) throws Exception {
        this.passSalt = SecurePass.getNewSalt();
        this.passHash = SecurePass.getPassHash(newPassword, passSalt);
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return Balance;
    }

    public void setBalance(BigDecimal balance) {
        Balance = balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
