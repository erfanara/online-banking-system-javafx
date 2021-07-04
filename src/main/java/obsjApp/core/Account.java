package obsjApp.core;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Account extends RecursiveTreeObject<Account> implements Serializable {
    public static final long ACC_ID_START = 6037000000000000L;

    // TODO: type of Account should specified using enum or maybe inheritance ? inheritance
    public static enum Type {
        SAVING, CHECKING;
    }

    protected Type type;
    private String alias;

    // Account id is a 16-digit number that we obtain this number using the hashcode of the Account object
    private final String id;

    private final User owner;

    protected BigDecimal balance = new BigDecimal(0);

    private final LocalDateTime creationDate = LocalDateTime.now();

    // encrypted password along with it's salt
    private String passHash;
    private String passSalt;

    protected final ArrayList<Transaction> transactions;


    // alias in parameters can be null
    public Account(String password, String alias, User owner) {
        this.owner = owner;
        this.alias = alias;
        this.id = generateId();
        setPassHash(password);
        type = Type.CHECKING;
        transactions = new ArrayList<Transaction>();
    }

    public Account(String password, String alias, BigDecimal balance, User owner) {
        this(password, alias, owner);
        this.balance = balance;
        type = Type.CHECKING;
    }

    // preventing race conditions using synchronized for withdraw,deposite methods
    public boolean withdraw(BigDecimal amount, Account toAccId) {
        synchronized (balance) {
            if (getBalance().compareTo(amount) < 0) {
                transactions.add(new Transaction(Transaction.Reason.TRANSFER, Transaction.Type.WITHDRAW, toAccId, amount, getBalance(), false));
                return false;
            }
            setBalance(getBalance().add(amount.negate()));
            transactions.add(new Transaction(Transaction.Reason.TRANSFER, Transaction.Type.WITHDRAW, toAccId, amount, getBalance(), true));
            toAccId.deposit(amount, this);
            return true;
        }
    }

    public void deposit(BigDecimal amount, Account fromAcc) {
        synchronized (balance) {
            setBalance(getBalance().add(amount));
            transactions.add(new Transaction(Transaction.Reason.TRANSFER, Transaction.Type.DEPOSIT, fromAcc, amount, getBalance(), true));
        }
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    // authenticate for Account
    public boolean auth(String password) {
        return this.passHash.equals(SecurePass.getPassHash(password, this.passSalt));
    }

    public void setPassHash(String newPassword) {
        this.passSalt = SecurePass.getNewSalt();
        this.passHash = SecurePass.getPassHash(newPassword, passSalt);
    }

    public String getType() {
        return type.toString();
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCreationDate() {
        return creationDate.toString();
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public User getOwner() {
        return owner;
    }

    private static String generateId() {
        String id = String.valueOf(ACC_ID_START + new Random().nextInt(999999999));
        while (User.allAccounts.containsKey(id)) {
            id = String.valueOf(ACC_ID_START + new Random().nextInt(999999999));
        }
        return id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + this.getId() +
                ", owner=" + owner.getFirstname() + "~" + owner.getLastName() +
                ", type=" + type +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                '}';
    }
}

