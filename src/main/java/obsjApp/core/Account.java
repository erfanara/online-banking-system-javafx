package obsjApp.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Account implements Serializable {
    // TODO: type of Account should specified using enum or maybe inheritance
    private String alias;
    private String id;
    private BigDecimal Balance;
    private final LocalDateTime creationDate = LocalDateTime.now();
    // TODO: constructors should updated
    private String passHash;
    private final ArrayList<Transaction> transactions;

    public Account(String id) {
        this.id = id;
        transactions = new ArrayList<>();
    }

    public Account(String alias, String id) {
        this.alias = alias;
        this.id = id;
        transactions = new ArrayList<>();
    }

    public Account(String alias, String id, BigDecimal balance) {
        this.alias = alias;
        this.id = id;
        Balance = balance;
        transactions = new ArrayList<>();
    }

    public void withdraw(BigDecimal amount) {
        if (getBalance().compareTo(amount) == -1) {
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

    public void setPassHash(String passHash) {
        this.passHash = passHash;
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

