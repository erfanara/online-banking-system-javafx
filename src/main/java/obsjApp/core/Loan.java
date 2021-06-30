package obsjApp.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Loan {

    private final BigDecimal amount;
    private final LocalDateTime loan_date;

    Loan(BigDecimal amount) {
        this.amount = amount;
        loan_date = LocalDateTime.now();
    }

    public LocalDateTime getLoan_date() {
        return loan_date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
