package OBSApp.core;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private final LocalDateTime creationDate = LocalDateTime.now();

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

    public String getPeerObj() {
        if (peerObj instanceof Account) {
            return ((Account) peerObj).getId();
        } else if (peerObj instanceof Bill) {
            return ((Bill) peerObj).getId() + ((Bill) peerObj).getPaymentId();
        } else if (peerObj instanceof Loan) {
            return peerObj.toString();
        }
        return null;
    }

    public Reason getReason() {
        return reason;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
