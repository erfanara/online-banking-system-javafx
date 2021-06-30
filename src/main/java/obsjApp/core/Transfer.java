package core;

import core.Account;

import java.math.BigDecimal;

public class Transfer {
    private final BigDecimal amount;
    Account source_account;
    Account target_account;
    String description;

    public Transfer(BigDecimal amount, Account source_account, Account target_account) {
        this.amount = amount;
        this.source_account = source_account;
        this.target_account = target_account;
    }

    public Transfer(BigDecimal amount, Account source_account, Account target_account, String description) {
        this.amount = amount;
        this.source_account = source_account;
        this.target_account = target_account;
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void PerformTransfer(){ //Feel free to change this code it's just in it's optimal state
        source_account.withdraw(amount);
        target_account.Deposit(amount);
    }
}
