package obsjApp.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Loan {

    private final BigDecimal amount;
    private final int interest;
    private final LocalDateTime creationDate = LocalDateTime.now();

    Loan(BigDecimal amount, int interest) {
        this.amount = amount;
        this.interest = interest;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}