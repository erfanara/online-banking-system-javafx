package obsjApp.core;

import java.io.Serializable;
import java.math.BigDecimal;

public class Transaction implements Serializable {
    public static enum Type{
        WITHDRAW,DEPOSIT;
    }
    private final Type type;
    private BigDecimal amount;
    private final BigDecimal balance;
    private String description;
    private final boolean isSuccessful;

    Transaction(Type type, BigDecimal amount, BigDecimal balance, boolean isSuccessful) {
        this.balance = balance;
        this.type = type;
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
