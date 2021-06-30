package obsjApp.core;

import java.io.Serializable;
import java.math.BigDecimal;

public class Transaction implements Serializable {
    public static enum Type{
        WITHDRAW,DEPOSIT;
    }
    private final Type type;
    private final Account peerAcc;
    private BigDecimal amount;
    private final BigDecimal balance;
    private String description;
    private final boolean isSuccessful;

    Transaction(Type type,Account peerAcc, BigDecimal amount, BigDecimal balance, boolean isSuccessful) {
        this.peerAcc = peerAcc;
        this.type = type;
        this.balance = balance;
        this.amount = amount;
        this.isSuccessful = isSuccessful;
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
