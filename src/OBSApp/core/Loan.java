package OBSApp.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Loan {

    private final BigDecimal amount;
    private final LocalDateTime creationDate = LocalDateTime.now();

    Loan(BigDecimal amount, int interest) {
        this.amount = amount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
