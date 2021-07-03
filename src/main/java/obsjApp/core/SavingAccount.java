package obsjApp.core;

import java.math.BigDecimal;

public class SavingAccount extends Account {

    public SavingAccount(String password, String alias, User owner) {
        super(password, alias, owner);
        type = Type.SAVING;
    }

    public SavingAccount(String password, String alias, BigDecimal balance, User owner) {
        super(password, alias, balance, owner);
        type = Type.SAVING;
    }
}
