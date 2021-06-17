package sample;

import java.io.Serializable;

public class Transaction implements Serializable {
    private final char type;
    private long amount = 0;
    private final long balance;
    private String description;
    private final boolean isSuccessful;

    Transaction(char type, long amount, long balance, boolean isSuccessful) {
        this.balance = balance;
        this.type = type;
        this.amount = amount;
        this.isSuccessful = isSuccessful;
    }

    public char getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
