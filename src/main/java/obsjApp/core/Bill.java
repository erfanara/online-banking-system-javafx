package obsjApp.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill {
    public enum Type {
        WATER(1),
        POWER(2),
        GAS(3),
        PHONE(4);

        private int value;

        private Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private final Type type;
    private final BigDecimal amount;
    private final BigDecimal remainingDebt;
    private final LocalDateTime creationDate = LocalDateTime.now();
    private final String subsidiaryCompanyId;
    private final String id;
    private final String paymentId;
    private boolean wasPaid;

    Bill(Type type, BigDecimal amount, BigDecimal remainingDebt, String subsidiaryCompanyId, String userHash) {
        this.type = type;
        this.amount = amount.add(remainingDebt);
        this.remainingDebt = remainingDebt;
        this.subsidiaryCompanyId = subsidiaryCompanyId;

        this.id = userHash + subsidiaryCompanyId + type.getValue();
        // TODO: adding Billing period to the paymentId
        this.paymentId = this.amount.toString() + creationDate.getYear();
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
