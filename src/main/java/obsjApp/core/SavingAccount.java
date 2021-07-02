package obsjApp.core;

import java.math.BigDecimal;

public class SavingAccount extends Account {

    public SavingAccount(String password, String alias) throws Exception {
        super(password, alias);
    }

    public SavingAccount(String password, String alias, BigDecimal balance) throws Exception {
        super(password, alias, balance);
    }
}
