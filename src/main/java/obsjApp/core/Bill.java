package obsjApp.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill {
    public enum Type {
        POWER,
        WATER,
        GAS,
        PHONE
    }

    private final BigDecimal amount;
    private LocalDateTime bill_date;

    Bill(BigDecimal amount){
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
