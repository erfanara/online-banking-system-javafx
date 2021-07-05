package OBSApp.core;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.io.Serializable;
import java.math.BigDecimal;

import java.io.Serializable;
import java.math.BigDecimal;

public class Transaction extends RecursiveTreeObject<Transaction> implements Serializable {
    public static enum Type {
        WITHDRAW, DEPOSIT;
    }

    public static enum Reason {
        BILL, LOAN, TRANSFER;
    }

    private final Reason reason;
    private final Type type;
    // this object can be Account object, Bill object or Loan object
    private final Object peerObj;
    private BigDecimal amount;
    private final BigDecimal balance;
    private String description;
    private final boolean isSuccessful;

    public Transaction(Reason reason,
                       Type type,
                       Account peerObj,
                       BigDecimal amount,
                       BigDecimal balance,
                       boolean isSuccessful) {
        this.reason = reason;
        this.type = type;
        this.peerObj = peerObj;
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