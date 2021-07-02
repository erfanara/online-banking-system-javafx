package obsjApp.core;

import java.math.BigDecimal;

public class CheckingAccount extends Account{

    public CheckingAccount(String password, String alias) throws Exception {
        super(password, alias);
    }

    public CheckingAccount(String password, String alias, BigDecimal balance) throws Exception {
        super(password, alias, balance);
    }
}
