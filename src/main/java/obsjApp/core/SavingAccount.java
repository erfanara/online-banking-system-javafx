package obsjApp.core;

import java.math.BigDecimal;

public class SavingAccount extends Account {

    public SavingAccount(String password, String alias){
        super(password, alias);
        type = Type.SAVING;
    }

    public SavingAccount(String password, String alias, BigDecimal balance){
        super(password, alias, balance);
        type = Type.SAVING;
    }
}
