package obsjApp.server;

import obsjApp.core.Account;
import obsjApp.core.Transaction;

import java.math.BigDecimal;

public final class MotherAccount extends Account {
    private final String id = String.valueOf(this.hashCode());

    public MotherAccount(String password, String alias) {
        super(password, null, null);
    }

    public MotherAccount(String password, String alias, BigDecimal balance) {
        super(password, null, balance, null);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean withdraw(BigDecimal amount, Account toAccId) {
        synchronized (balance) {
            setBalance(getBalance().add(amount.negate()));
            transactions.add(new Transaction(Transaction.Reason.TRANSFER, Transaction.Type.WITHDRAW, toAccId, amount, getBalance(), true));
            toAccId.deposit(amount, this);
        }
        return true;
    }

    @Override
    public void deposit(BigDecimal amount, Account fromAcc) {
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public void setAlias(String alias) {
    }

    @Override
    public String getType() {
        return null;
    }
}
